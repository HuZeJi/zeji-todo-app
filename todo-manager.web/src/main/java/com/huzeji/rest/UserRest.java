package com.huzeji.rest;

import com.huzeji.model.Task;
import com.huzeji.model.User;
import com.huzeji.todomanager.svc.GenericCrudSvc;
import com.huzeji.todomanager.svc.TaskSvc;
import com.huzeji.todomanager.svc.UserSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
@RequestMapping( "/v1/users" )
public class UserRest extends GenericRest<User, Long>{

    @Autowired private UserSvc userSvc;

    @Override
    public GenericCrudSvc<User, Long> getService() { return userSvc; }

    // User tasks
    @GetMapping( "/{userId}/tasks" )
    public Page<Task> getTasks( @PathVariable( "userId" ) Long userId, @RequestParam Map<String, Object> filters  ){
        return userSvc.getTasks( userId, filters );
    }
}
