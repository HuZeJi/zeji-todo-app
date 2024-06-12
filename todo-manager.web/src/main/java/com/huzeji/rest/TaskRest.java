package com.huzeji.rest;

import com.huzeji.model.Task;
import com.huzeji.todomanager.svc.TaskSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/v1/tasks" )
public class TaskRest {

    @Autowired private TaskSvc taskSvc;

    @PostMapping()
    public Task createTask( @RequestBody Task task ) {
        return taskSvc.createTask( task );
    }

    // TODO: improve error output
    @PatchMapping( "/{taskId}/{completeAction}" )
    public Task completeTask(
            @PathVariable( "taskId" ) Long taskId,
            @PathVariable( "completeAction" ) String completeAction
    ) throws NoSuchMethodException
    {
        return taskSvc.taskAction( taskId, completeAction );
    }

    @DeleteMapping( "/{taskId}" )
    public Task deleteTask( @PathVariable( "taskId" ) Long taskId ){
        return taskSvc.deleteTask( taskId );
    }
}
