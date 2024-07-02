package com.huzeji.todomanager.svc.impl;

import com.huzeji.model.TaskEntity;
import com.huzeji.model.UserEntity;
import com.huzeji.model.dto.TaskDto;
import com.huzeji.todomanager.repository.TaskRepository;
import com.huzeji.todomanager.svc.TaskSvc;
import com.huzeji.todomanager.svc.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
class TaskSvcImpl implements TaskSvc {
    @Autowired private TaskRepository repository;
    @Override
    public Page<TaskEntity> getTasks(Map<String, Object> filters ) {
        return repository.getPage( filters, PageUtil.buildPageRequest( filters ) );
    }

    @Override
    public TaskEntity persistTask(TaskDto task, UserEntity user) {
        return repository.save(new TaskEntity(task, user));
    }

    @Override
    public TaskEntity persistTask(TaskEntity task) {
        return repository.save( task );
    }
}
