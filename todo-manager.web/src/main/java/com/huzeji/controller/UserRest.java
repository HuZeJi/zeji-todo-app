package com.huzeji.controller;

import com.huzeji.model.UserEntity;
import com.huzeji.model.dto.TaskDto;
import com.huzeji.todomanager.svc.GenericCrudSvc;
import com.huzeji.todomanager.svc.TaskManagementSvc;
import com.huzeji.todomanager.svc.UserSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
@RequestMapping( "/secure/api/v1/users" )
public class UserRest extends GenericRest<UserEntity, Long>{

    @Autowired private UserSvc userSvc;
    @Autowired private TaskManagementSvc taskManager;

    @Override
    public GenericCrudSvc<UserEntity, Long> getService() { return userSvc; }

    // User tasks
    @GetMapping( "/{userId}/tasks" )
    public Page<TaskDto> getTasks(@PathVariable( "userId" ) Long userId, @RequestParam Map<String, Object> filters  ){
        return taskManager.getTasks( userId, filters );
    }
}
