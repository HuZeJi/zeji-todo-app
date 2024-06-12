package com.huzeji.todomanager.repository;

import com.huzeji.model.Task;
import com.huzeji.todomanager.repository.generic.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface TaskRepository extends GenericRepository<Task, Long> {
    default Page<Task> getPage( Map<String, Object> filters, Pageable page ){
        return findAll( filter( filters ), page );
    }
}
