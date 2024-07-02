package com.huzeji.model.dto;

import com.huzeji.model.TagEntity;
import com.huzeji.model.TaskEntity;
import com.huzeji.model.enums.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private Boolean isComplete ;
    private Long userId;
    private StatusEnum status;
    private LocalDateTime time;
    private List<String> tags;
    public TaskDto(TaskEntity task ) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.isComplete = task.getIsComplete();
        this.userId = task.getUser().getId();
        this.status = task.getStatus();
        this.time = task.getTime();

        if( task.getTags() != null )
            this.tags = task.getTags().stream().map(TagEntity::getName).toList();
    }
}
