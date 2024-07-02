package com.huzeji.todomanager.svc;

import com.huzeji.model.TaskEntity;
import com.huzeji.model.UserEntity;
import com.huzeji.model.dto.TaskDto;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface TaskSvc {
    Page<TaskEntity> getTasks(Map<String, Object> filters);
    TaskEntity persistTask(TaskDto task, UserEntity user);
    TaskEntity persistTask(TaskEntity task);
}
