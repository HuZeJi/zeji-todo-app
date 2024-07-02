package com.huzeji.todomanager.repository;

import com.huzeji.model.TaskEntity;
import com.huzeji.todomanager.repository.generic.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends GenericRepository<TaskEntity, Long> {
}
