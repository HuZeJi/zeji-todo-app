package com.huzeji.controller;

import com.huzeji.model.UserEntity;
import com.huzeji.todomanager.svc.GenericCrudSvc;
import com.huzeji.todomanager.svc.TaskManagementSvc;
import com.huzeji.todomanager.svc.UserSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping( "/public/api/v1/users" )
public class PublicUserRest extends GenericRest<UserEntity, Long>{

    @Autowired private UserSvc userSvc;
    @Autowired private TaskManagementSvc taskManager;

    @Override
    public GenericCrudSvc<UserEntity, Long> getService() { return userSvc; }
}
