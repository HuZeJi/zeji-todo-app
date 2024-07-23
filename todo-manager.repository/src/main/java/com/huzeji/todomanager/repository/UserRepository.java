package com.huzeji.todomanager.repository;

import com.huzeji.model.UserEntity;
import com.huzeji.todomanager.repository.generic.GenericRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
public interface UserRepository extends GenericRepository<UserEntity, Long> {}
