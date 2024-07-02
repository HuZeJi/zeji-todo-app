package com.huzeji.todomanager.svc.impl;

import com.huzeji.model.UserEntity;
import com.huzeji.model.enums.StatusEnum;
import com.huzeji.todomanager.repository.generic.GenericRepository;
import com.huzeji.todomanager.repository.UserRepository;
import com.huzeji.todomanager.svc.UserSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
class UserSvcImpl extends GenericCrudSvcImpl<UserEntity, Long> implements UserSvc {

    @Autowired private UserRepository repository;
    @Override
    public GenericRepository<UserEntity, Long> getRepository() { return repository; }

    @Override
    public void delete( Long identifier ) {
        UserEntity registry = getRepository().findById( identifier ).orElseThrow(NoSuchElementException::new);
        registry.setStatus( StatusEnum.INACTIVE );
        this.create( registry );
    }

    // TODO: improve update method
    @Override
    public UserEntity update(UserEntity registry, Long identifier) {
        getRepository().findById( identifier ).orElseThrow(NoSuchElementException::new);
        registry.setId( identifier );
        return this.create( registry );
    }
}
