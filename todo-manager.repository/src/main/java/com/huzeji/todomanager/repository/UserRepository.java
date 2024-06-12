package com.huzeji.todomanager.repository;

import com.huzeji.model.User;
import com.huzeji.todomanager.repository.generic.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GenericRepository<User, Long> {}
