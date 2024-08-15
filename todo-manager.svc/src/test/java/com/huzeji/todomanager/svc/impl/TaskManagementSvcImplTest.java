package com.huzeji.todomanager.svc.impl;

import com.huzeji.model.TagEntity;
import com.huzeji.model.TaskEntity;
import com.huzeji.model.UserEntity;
import com.huzeji.model.dto.TaskDto;
import com.huzeji.model.enums.StatusEnum;
import com.huzeji.todomanager.svc.TaskSvc;
import com.huzeji.todomanager.svc.UserSvc;
import com.huzeji.todomanager.svc.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.scheduling.config.Task;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskManagementSvcImplTest {

    @InjectMocks private TaskManagementSvcImpl taskManagementSvc;
    @Mock private TaskSvc taskSvc;
    @Mock private JwtUtil jwtUtil;
    @Mock private UserSvc userSvc;

    private static final String ANY_STRING = "3";
    private static final Long ANY_LONG = 3L;

    /*
    * CREATE TASK:
    * 1. when user is not retrieved from token, then throw exception
    * 2. when persist task is successful, then return task
    * 3. when persist task is unsuccessful, then throw exception
    * */

    @Test
    void testCreateTaskCaseUserNotFound() {
        // Arrange
        when( jwtUtil.getUserFromToken( anyString() ) ).thenReturn( null );
        // Act && Assert
        assertThrows(
                NullPointerException.class,
                () -> taskManagementSvc.createTask( new TaskDto(), ANY_STRING )
        );

        // Another way to check the exception
        try { taskManagementSvc.createTask( new TaskDto(), ANY_STRING ); }
        catch ( NullPointerException ex ) {
            Class<?> expected = NullPointerException.class;
            Class<?> result = ex.getClass();
            assertEquals( expected, result );
        }
    }

    @Test
    void testCreateTaskCaseSuccessfulPersist(){
        // Arrange
        UserEntity user = new UserEntity();
        user.setId( ANY_LONG );
        user.setNickname( ANY_STRING );
        when( jwtUtil.getUserFromToken( anyString() ) ).thenReturn( user );

        TaskEntity task = new TaskEntity();
        task.setOwner( user );
        when( taskSvc.persistTask( any( TaskDto.class ), any( UserEntity.class ) ) )
                .thenReturn( task );
        // Act
        TaskDto result = taskManagementSvc.createTask( new TaskDto(), ANY_STRING );
        // Assert
        assertNotNull( result );
    }

    @Test
    void testCreateTaskCaseUnsuccessfulPersist(){
        // Arrange
        UserEntity user = new UserEntity();
        user.setId( ANY_LONG );
        user.setNickname( ANY_STRING );
        when( jwtUtil.getUserFromToken( anyString() ) ).thenReturn( user );

        when( taskSvc.persistTask( any( TaskDto.class ), any( UserEntity.class ) ) )
                .thenReturn( null );
        // Act && Assert
        assertThrows(
                NullPointerException.class,
                () -> taskManagementSvc.createTask( new TaskDto(), ANY_STRING )
        );
    }


    /*
    * COMPLETE TASK & UNDO COMPLETE TASK:
    * 1. when task successfully change state, then return task
    * 2. when task is not found, then throw exception
    * 3. when task is already in the same state, then return task
    *
    * INPUTS:
    * 1. task id
    *
    * RESOURCE:
    * 1. get task entity
    * 2. persist new task entity
    *
    * OUTPUTS::
    * 1. task
    *
    * VALIDATION:
    * 1. Has the same status ? no change : new status
    * 2. Is task found ? - : throw exception
    * */

    @Test
    void testCompleteTaskCaseSuccessfulChangeOfState() {
        TaskEntity task = new TaskEntity();
        task.setOwner( new UserEntity() );
        task.setIsComplete( false );
        Page<TaskEntity> taskPage = new PageImpl<>( Collections.singletonList( task ) );
        when( taskSvc.getTasks( anyMap() ) ).thenReturn( taskPage );

        TaskEntity completedTask = new TaskEntity();
        completedTask.setOwner( new UserEntity() );
        completedTask.setIsComplete( true );
        when( taskSvc.persistTask( any( TaskEntity.class ) ) ).thenReturn( completedTask );

        TaskDto result = taskManagementSvc.completeTask( ANY_LONG );

        assertTrue( result.getIsComplete() );
    }

    @Test
    void testCompleteTaskCaseTaskNotFound() {
        Page<TaskEntity> taskPage = new PageImpl<>( Collections.emptyList() );
        when( taskSvc.getTasks( anyMap() ) ).thenReturn( taskPage );

        Exception exception = assertThrows(
                ResponseStatusException.class,
                () -> taskManagementSvc.completeTask( ANY_LONG )
        );
        String expected = "204 NO_CONTENT \"Task not found with id [" + ANY_LONG + "].\"";
        String result = exception.getMessage();
        assertEquals( expected, result );
    }

    @Test
    void testCompleteTaskCaseTaskHasNoChange() {
        TaskEntity task = new TaskEntity();
        task.setOwner( new UserEntity() );
        task.setIsComplete( true );
        Page<TaskEntity> taskPage = new PageImpl<>( Collections.singletonList( task ) );
        when( taskSvc.getTasks( anyMap() ) ).thenReturn( taskPage );

        TaskDto result = taskManagementSvc.completeTask( ANY_LONG );

        assertTrue( result.getIsComplete() );
        verify( taskSvc, times( 0 ) ).persistTask( any( TaskEntity.class ) );
    }

    @Test
    void testUndoCompleteTaskCaseSuccessfulChangeOfState() {
        TaskEntity task = new TaskEntity();
        task.setOwner( new UserEntity() );
        task.setIsComplete( true );
        Page<TaskEntity> taskPage = new PageImpl<>( Collections.singletonList( task ) );
        when( taskSvc.getTasks( anyMap() ) ).thenReturn( taskPage );

        TaskEntity completedTask = new TaskEntity();
        completedTask.setOwner( new UserEntity() );
        completedTask.setIsComplete( false );
        when( taskSvc.persistTask( any( TaskEntity.class ) ) ).thenReturn( completedTask );

        TaskDto result = taskManagementSvc.undoCompleteTask( ANY_LONG );

        assertFalse( result.getIsComplete() );
    }

    @Test
    void testUndoCompleteTaskCaseTaskNotFound() {
        Page<TaskEntity> taskPage = new PageImpl<>( Collections.emptyList() );
        when( taskSvc.getTasks( anyMap() ) ).thenReturn( taskPage );

        Exception exception = assertThrows(
                ResponseStatusException.class,
                () -> taskManagementSvc.undoCompleteTask( ANY_LONG )
        );
        String expected = "204 NO_CONTENT \"Task not found with id [" + ANY_LONG + "].\"";
        String result = exception.getMessage();
        assertEquals( expected, result );
    }

    @Test
    void testUndoCompleteTaskCaseTaskHasNoChange() {
        TaskEntity task = new TaskEntity();
        task.setOwner( new UserEntity() );
        task.setIsComplete( false );
        Page<TaskEntity> taskPage = new PageImpl<>( Collections.singletonList( task ) );
        when( taskSvc.getTasks( anyMap() ) ).thenReturn( taskPage );

        TaskDto result = taskManagementSvc.undoCompleteTask( ANY_LONG );

        assertFalse( result.getIsComplete() );
        verify( taskSvc, times( 0 ) ).persistTask( any( TaskEntity.class ) );
    }

    /*
    * DELETE TASK:    *
    * INPUTS:
    * 1. task id
    *
    * OUTPUTS:
    * 1. deleted task
    *
    * INTERACTIONS:
    * 1. get task entity
    * 2. persist task entity
    *
    * VALIDATIONS:
    * 1. if task doesn't exist ? throw exception
    * 2. if task is already deleted ? return task with no change
    * 3. if task is not deleted ? delete task
    *
    * SCENARIOS:
    * 1. task is ok, then delete task
    * 2. task not found, then throw 204 exception
    * 3. task already deleted, then return task with no change
    * */

    @Test
    void testDeleteTaskCaseSuccessfulDelete() {
        TaskEntity task = new TaskEntity();
        task.setOwner( new UserEntity() );
        task.setStatus( StatusEnum.ACTIVE );
        Page<TaskEntity> taskPage = new PageImpl<>( Collections.singletonList( task ) );
        when( taskSvc.getTasks( anyMap() ) ).thenReturn( taskPage );

        TaskEntity deletedTask = new TaskEntity();
        deletedTask.setOwner( new UserEntity() );
        deletedTask.setStatus( StatusEnum.INACTIVE );
        when( taskSvc.persistTask( any( TaskEntity.class ) ) ).thenReturn( deletedTask );

        TaskDto result = taskManagementSvc.deleteTask( ANY_LONG );

        assertEquals( StatusEnum.INACTIVE, result.getStatus() );
    }

    @Test
    void testDeleteTaskCaseTaskNotFound() {
        Page<TaskEntity> taskPage = new PageImpl<>( Collections.emptyList() );
        when( taskSvc.getTasks( anyMap() ) ).thenReturn( taskPage );

        Exception exception = assertThrows(
                ResponseStatusException.class,
                () -> taskManagementSvc.deleteTask( ANY_LONG )
        );
        String expected = "204 NO_CONTENT \"Task not found with id [" + ANY_LONG + "].\"";
        String result = exception.getMessage();
        assertEquals( expected, result );
    }

    @Test
    void testDeleteTaskCaseTaskAlreadyDeleted() {
        TaskEntity task = new TaskEntity();
        task.setOwner( new UserEntity() );
        task.setStatus( StatusEnum.INACTIVE );
        Page<TaskEntity> taskPage = new PageImpl<>( Collections.singletonList( task ) );
        when( taskSvc.getTasks( anyMap() ) ).thenReturn( taskPage );

        TaskDto result = taskManagementSvc.deleteTask( ANY_LONG );

        assertEquals( StatusEnum.INACTIVE, result.getStatus() );
        verify( taskSvc, times( 0 ) ).persistTask( any( TaskEntity.class ) );
    }

    /*
    * GET TASK
    * INPUTS:
    * 1. user id
    * 2. filters
    *
    * OUTPUTS:
    * 1. tasks by user
    *
    * INTERACTIONS:
    * 1. get task entities
    *
    * VALIDATIONS:
    * 1. if filters are null ? empty filter
    * 2. if tasks is not founded ? empty page
    *
    * SCENARIOS:
    * 1. when filters are empty, return tasks by user
    * 2. when tasks are not found, return empty page
    * */
    @Test
    void testGetTasksCaseSuccessfullyDataRetrieved() {
        // arrange
        UserEntity owner = new UserEntity();
        owner.setId( ANY_LONG );

        TaskEntity task = new TaskEntity();
        task.setOwner( owner );
        Page<TaskEntity> taskPage = new PageImpl<>( Collections.singletonList( task ) );
        when( taskSvc.getTasks( anyMap() ) ).thenReturn( taskPage );

        // act
        Map<String, Object> filters = new HashMap<>();
        filters.put( ANY_STRING, ANY_LONG );
        Page<TaskDto> result = taskManagementSvc.getTasks( ANY_LONG, filters );

        // assert
        assertFalse( result.isEmpty() );
    }

    @Test
    void testGetTasksCaseNoFilters() {
        // arrange
        UserEntity owner = new UserEntity();
        owner.setId( ANY_LONG );

        TaskEntity task = new TaskEntity();
        task.setOwner( owner );
        Page<TaskEntity> taskPage = new PageImpl<>( Collections.singletonList( task ) );
        when( taskSvc.getTasks( anyMap() ) ).thenReturn( taskPage );

        // act
        Page<TaskDto> result = taskManagementSvc.getTasks( ANY_LONG, null );

        // assert
        assertFalse( result.isEmpty() );
    }

    @Test
    void testGetTasksCaseNoDataFound() {
        // arrange
        Page<TaskEntity> taskPage = new PageImpl<>( Collections.emptyList() );
        when( taskSvc.getTasks( anyMap() ) ).thenReturn( taskPage );

        // act
        Map<String, Object> filters = new HashMap<>();
        filters.put( ANY_STRING, ANY_LONG );
        Page<TaskDto> result = taskManagementSvc.getTasks( ANY_LONG, filters );

        // assert
        assertTrue( result.isEmpty() );
    }

    /*
    * ADD TAGS
    * INPUT:
    * 1. list of tags
    * 2. task identifier
    *
    * OUTPUT:
    * 1. task with tags added
    *
    * CONSTRAINTS:
    * 1. if task doesn't exist, then throw exception
    * 2. if task has more than 5 tags, then throw exception
    * 3. if tag already exist, then tag is not added to the task
    *
    * CASES:
    * 1. task doesn't exist, expected no content exception
    * 2. task and tags to add count is more than 5, expected bad request exception
    * 3. tags already exist on the task, expected tags not added
    * 4. tags added successfully, expected tags added
    * */
    @Test
    void testAddTagsCaseTagsAddedSuccessfully() {
        // arrange
        UserEntity owner = new UserEntity();
        owner.setId( ANY_LONG );
        TaskEntity task = new TaskEntity();
        task.setOwner( owner );
        task.setTags( new ArrayList<>() );
        when( taskSvc.getTasks( anyMap() ) )
                .thenReturn( new PageImpl<>( Collections.singletonList( task ) ) );
        when( taskSvc.persistTask( any( TaskEntity.class ) ) )
                .thenReturn( task );

        // act
        String tag = "Tag 1";
        TaskDto result = taskManagementSvc.addTags( Collections.singletonList( tag ), ANY_LONG );

        // assert
        assertNotNull( result );
        assertNotNull( result.getTags() );
        assertFalse( result.getTags().isEmpty() );
        assertEquals( 1, result.getTags().size() );
        assertEquals( tag, result.getTags().getFirst() );
    }

    @Test
    void testAddTagsCaseNoTaskFounded() {
        // arrange
        when( taskSvc.getTasks( anyMap() ) )
                .thenReturn( new PageImpl<>( Collections.emptyList() ) );

        // act
        String tag = "Tag 1";

        Exception exception = assertThrows(
                ResponseStatusException.class,
                () -> taskManagementSvc.addTags( Collections.singletonList( tag ), ANY_LONG )
        );

        String expected = "204 NO_CONTENT \"Task not found with id [" + ANY_LONG + "].\"";
        String result = exception.getMessage();
        assertEquals( expected, result );
    }

    @Test
    void testAddTagsCaseMoreThan5Tags() {
        // arrange
        UserEntity owner = new UserEntity();
        owner.setId( ANY_LONG );
        TaskEntity task = new TaskEntity();
        task.setOwner( owner );
        task.setTags( Arrays.asList(
                new TagEntity( "Tag 1", task )
                , new TagEntity( "Tag 2", task )
                , new TagEntity( "Tag 3", task )
                , new TagEntity( "Tag 4", task )
                , new TagEntity( "Tag 5", task )
        ) );
        when( taskSvc.getTasks( anyMap() ) )
                .thenReturn( new PageImpl<>( Collections.singletonList( task ) ) );

        // act
        String tag = "Tag 1";

        Exception exception = assertThrows(
                ResponseStatusException.class,
                () -> taskManagementSvc.addTags( Collections.singletonList( tag ), ANY_LONG )
        );

        String expected = "400 BAD_REQUEST \"Task can have at most 5 tags.\"";
        String result = exception.getMessage();
        assertEquals( expected, result );
    }

    @Test
    void testAddTagsCaseTagAlreadyExist() {
        // arrange
        String tag = "Tag 1";

        UserEntity owner = new UserEntity();
        owner.setId( ANY_LONG );
        TaskEntity task = new TaskEntity();
        task.setOwner( owner );
        task.setTags( List.of( new TagEntity( tag, task ) ) );
        when( taskSvc.getTasks( anyMap() ) )
                .thenReturn( new PageImpl<>( Collections.singletonList( task ) ) );
        when( taskSvc.persistTask( any( TaskEntity.class ) ) )
                .thenReturn( task );

        // act
        TaskDto result = taskManagementSvc.addTags( Collections.singletonList( tag ), ANY_LONG );

        // assert
        assertNotNull( result );
        assertNotNull( result.getTags() );
        assertFalse( result.getTags().isEmpty() );
        assertEquals( 1, result.getTags().size() );
        assertEquals( tag, result.getTags().getFirst() );
    }
}