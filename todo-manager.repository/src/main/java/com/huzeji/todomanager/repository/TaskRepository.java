package com.huzeji.todomanager.repository;

import com.huzeji.model.TaskEntity;
import com.huzeji.todomanager.repository.generic.GenericRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
public interface TaskRepository extends GenericRepository<TaskEntity, Long> {
}
