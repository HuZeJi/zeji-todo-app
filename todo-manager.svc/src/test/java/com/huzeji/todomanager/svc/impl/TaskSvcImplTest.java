package com.huzeji.todomanager.svc.impl;

import com.huzeji.model.TaskEntity;
import com.huzeji.todomanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class )
class TaskSvcImplTest {

    @InjectMocks private TaskSvcImpl taskSvc;
    @Mock private TaskRepository repository;

    @Test
    void testGetTasksCaseOk() {
        TaskEntity task = new TaskEntity();
        List<TaskEntity> tasks = Collections.singletonList( task );
        Page<TaskEntity> page = new PageImpl<>( tasks );
        when( repository.getPage( anyMap(), any( Pageable.class ) ) ).thenReturn( page );

        Page<TaskEntity> tasksRetrieved = taskSvc.getTasks( new HashMap<>() );

        Integer result = tasksRetrieved.getSize();
        Integer expected = 1;
        assertEquals( expected, result );
    }

    @Test
    void testGetTasksCaseNoDataFound() {
        List<TaskEntity> tasks = new ArrayList<>();
        Page<TaskEntity> page = new PageImpl<>( tasks );
        when( repository.getPage( anyMap(), any( Pageable.class ) ) ).thenReturn( page );

        Page<TaskEntity> tasksRetrieved = taskSvc.getTasks( new HashMap<>() );

        Integer result = tasksRetrieved.getSize();
        Integer expected = 0;
        assertEquals( expected, result );
    }
}