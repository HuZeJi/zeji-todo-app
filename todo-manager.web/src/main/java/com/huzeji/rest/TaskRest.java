package com.huzeji.rest;

import com.huzeji.model.dto.TaskDto;
import com.huzeji.todomanager.svc.TaskManagementSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/secure/v1/tasks" )
public class TaskRest {
    @Autowired private TaskManagementSvc taskManager;

    @PostMapping()
    public TaskDto createTask(@RequestBody TaskDto task, @RequestHeader(value = "Authorization") String authorizationHeader) {
        return taskManager.createTask( task, authorizationHeader );
    }

    @PatchMapping( "/{taskId}/complete")
    public TaskDto completeTask( @PathVariable( "taskId" ) Long taskId ) {
        return taskManager.completeTask( taskId );
    }

    @PatchMapping( "/{taskId}/undo-complete")
    public TaskDto undoCompleteTask( @PathVariable( "taskId" ) Long taskId ) {
        return taskManager.undoCompleteTask( taskId );
    }
    @DeleteMapping( "/{taskId}" )
    public TaskDto deleteTask(@PathVariable( "taskId" ) Long taskId ){
        return taskManager.deleteTask( taskId );
    }

    @PutMapping( "/{taskId}/tags" )
    public TaskDto addTags( @RequestBody List<String> tags, @PathVariable( "taskId" ) Long taskId ) {
        return taskManager.addTags( tags, taskId );
    }

    @PutMapping( "/{taskId}/share-holders" )
    public TaskDto addShareHolders( @RequestBody List<Long> shareHolders, @PathVariable( "taskId" ) Long taskId ) {
        return taskManager.addShareHolders( shareHolders, taskId );
    }
}
