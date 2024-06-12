package com.huzeji.todomanager.svc.impl;

import com.huzeji.model.Task;
import com.huzeji.model.User;
import com.huzeji.model.enums.StatusEnum;
import com.huzeji.todomanager.repository.generic.GenericRepository;
import com.huzeji.todomanager.repository.UserRepository;
import com.huzeji.todomanager.svc.TaskSvc;
import com.huzeji.todomanager.svc.UserSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
class UserSvcImpl extends GenericCrudSvcImpl<User, Long> implements UserSvc {

    @Autowired private UserRepository repository;
    @Autowired private TaskSvc taskSvc;
    @Override
    public GenericRepository<User, Long> getRepository() { return repository; }

    @Override
    public void delete( Long identifier ) {
        User registry = getRepository().findById( identifier ).orElseThrow(NoSuchElementException::new);
        registry.setStatus( StatusEnum.INACTIVE );
        this.create( registry );
    }

    // TODO: improve update method
    @Override
    public User update(User registry, Long identifier) {
        getRepository().findById( identifier ).orElseThrow(NoSuchElementException::new);
        registry.setId( identifier );
        return this.create( registry );
    }

    @Override
    public Page<Task> getTasks(Long userId, Map<String, Object> filters) {
        if( filters == null ) filters = new HashMap<>();
        filters.put( "userId", userId );
        return taskSvc.getTasks( filters );
    }
}
