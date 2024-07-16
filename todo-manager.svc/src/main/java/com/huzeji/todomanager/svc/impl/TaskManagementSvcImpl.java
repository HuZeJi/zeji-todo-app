package com.huzeji.todomanager.svc.impl;

import com.huzeji.model.TagEntity;
import com.huzeji.model.TaskEntity;
import com.huzeji.model.UserEntity;
import com.huzeji.model.UserTaskEntity;
import com.huzeji.model.dto.TaskDto;
import com.huzeji.model.enums.StatusEnum;
import com.huzeji.todomanager.svc.TaskManagementSvc;
import com.huzeji.todomanager.svc.TaskSvc;
import com.huzeji.todomanager.svc.UserSvc;
import com.huzeji.todomanager.svc.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class TaskManagementSvcImpl implements TaskManagementSvc {
    @Autowired private JwtUtil jwtUtil;
    @Autowired private TaskSvc taskSvc;
    @Autowired private UserSvc userSvc;
    @Override
    public TaskDto createTask(TaskDto task, String authorizationHeader) {
        UserEntity user = jwtUtil.getUserFromToken( authorizationHeader );
        task.setOwnerId( user.getId() );
        return new TaskDto( taskSvc.persistTask( task, user ) );
    }

    @Override
    public TaskDto completeTask(Long taskId) {
        return new TaskDto( setNewCompleteStatus( taskId, Boolean.TRUE ) );
    }

    @Override
    public TaskDto undoCompleteTask(Long taskId) {
        return new TaskDto( setNewCompleteStatus( taskId, Boolean.FALSE ) );
    }

    @Override
    public TaskDto deleteTask(Long taskId) {
        TaskEntity task = getTaskById( taskId );
        // if already in the request status, not perform the database update
        if( task.getStatus().equals( StatusEnum.INACTIVE ) ) return new TaskDto( task );
        task.setStatus( StatusEnum.INACTIVE );
        return new TaskDto( taskSvc.persistTask( task ) );
    }

    private TaskEntity setNewCompleteStatus(Long taskId, Boolean isComplete ) {
        TaskEntity task = getTaskById( taskId );
        // if already in the request status, not perform the database update
        if( task.getIsComplete().equals( isComplete ) ) return task;
        task.setIsComplete( isComplete );
        return taskSvc.persistTask( task );
    }

    private TaskEntity getTaskById( Long taskId ) {
        Map<String, Object> filter = new HashMap<>();
        filter.put( "id", taskId );
        Page<TaskEntity> taskPage = taskSvc.getTasks( filter );
        if( taskPage.isEmpty() )
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Task not found with id [" + taskId + "].");
        return taskPage.getContent().getFirst();
    }

    @Override
    public Page<TaskDto> getTasks(Long userId, Map<String, Object> filters) {
        if( filters == null ) filters = new HashMap<>();
        filters.put( "owner.id", userId );
        Page<TaskEntity> taskPage = taskSvc.getTasks( filters );
        return taskPage.map( TaskDto::new );
    }

    @Override
    public TaskDto addTags( List<String> tags, Long taskId ) {
        TaskEntity task = getTaskById( taskId );

        if( task.getTags().size() > 5 )
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Task can have at most 5 tags." );

        tags.stream()
                .filter( tag -> !task.getTags().stream().map( TagEntity::getName ).toList().contains( tag ) )
                .forEach( tag -> task.getTags().add( new TagEntity( tag, task ) ) );
        return new TaskDto( taskSvc.persistTask(task) );
    }

    @Override
    public TaskDto addShareHolders(List<Long> shareHoldersIdList, Long taskId) {
        TaskEntity task = getTaskById(taskId);
        shareHoldersIdList.stream()
                .distinct()
                .filter( shareHolderId -> !isShareHolderAlreadyAdded(shareHolderId, task) )
                .map(this::getShareHolder)
                .forEach(shareHolder -> task.getSharedList().add(new UserTaskEntity(shareHolder, task)));
        return new TaskDto(taskSvc.persistTask(task));
    }

    private Boolean isShareHolderAlreadyAdded(Long shareHolderId, TaskEntity task) {
        return task.getSharedList().stream().anyMatch(sh -> sh.getShareholder().getId().equals(shareHolderId));
    }

    private UserEntity getShareHolder(Long shareHolderId) {
        return Optional.ofNullable(userSvc.get(getShareHoldersFilter(shareHolderId)).getContent().getFirst())
                .orElseThrow(() -> new NoSuchElementException("User not found with id [" + shareHolderId + "]."));
    }

    private Map<String, Object> getShareHoldersFilter( Long shareHolder ) {
        Map<String, Object> userFilter = new HashMap<>();
        userFilter.put( "id", shareHolder );
        return userFilter;
    }
}
