package com.huzeji.todomanager.svc.impl;

import com.huzeji.model.Task;
import com.huzeji.model.enums.StatusEnum;
import com.huzeji.todomanager.repository.TaskRepository;
import com.huzeji.todomanager.svc.TaskSvc;
import com.huzeji.todomanager.svc.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;

@Service
class TaskSvcImpl implements TaskSvc {

    @Autowired private TaskRepository repository;
    @Override
    public Page<Task> getTasks( Map<String, Object> filters ) {
        return repository.getPage( filters, PageUtil.buildPageRequest( filters ) );
    }

    // TODO: validate that user exists
    @Override
    public Task createTask(Task task) {
        return repository.save( task );
    }

    // TODO: add delete constraints
    @Override
    public Task deleteTask(Long taskId) {
        Task registry = repository.findById( taskId ).orElseThrow(NoSuchElementException::new);
        registry.setStatus( StatusEnum.INACTIVE );
        return createTask( registry );
    }

    // TODO: can implement factory strategy
    @Override
    public Task taskAction(Long taskId, String action) throws NoSuchMethodException {
        if( action.equals( "complete" ) ) return setNewCompleteStatus( taskId, Boolean.TRUE );
        if( action.equals( "undo-complete" ) ) return setNewCompleteStatus( taskId, Boolean.FALSE );
        throw new NoSuchMethodException( "Method not defined" );
    }

    private Task setNewCompleteStatus( Long taskId, Boolean newCompleteStatus ) {
        Task registry = repository.findById( taskId ).orElseThrow(NoSuchElementException::new);
        if( registry.getIsComplete().equals( newCompleteStatus ) ) return registry;
        registry.setIsComplete( newCompleteStatus );
        return createTask( registry );
    }
}
