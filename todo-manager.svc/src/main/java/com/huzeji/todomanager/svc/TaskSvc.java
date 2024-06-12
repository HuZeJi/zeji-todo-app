package com.huzeji.todomanager.svc;

import com.huzeji.model.Task;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface TaskSvc {
    public Page<Task> getTasks( Map<String, Object> filters);
    public Task createTask( Task task );
    public Task deleteTask( Long taskId );
    public Task taskAction( Long taskId, String action ) throws NoSuchMethodException;
}
