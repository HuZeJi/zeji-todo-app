package com.huzeji.todomanager.svc;

import com.huzeji.model.dto.TaskDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface TaskManagementSvc {
    TaskDto createTask(TaskDto task, String authorizationHeader );
    TaskDto completeTask( Long taskId );
    TaskDto undoCompleteTask( Long taskId );
    TaskDto deleteTask( Long taskId );
    Page<TaskDto> getTasks(Long userId, Map<String, Object> filters );
    TaskDto addTags( List<String> tags, Long taskId );
    TaskDto addShareHolders( List<Long> shareHolders, Long taskId );
}
