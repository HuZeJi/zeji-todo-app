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
    private Long ownerId;
    private StatusEnum status;
    private LocalDateTime time;
    private List<String> tags;
    private List<ShareHolderDto> shareHolders;
    public TaskDto(TaskEntity task ) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.isComplete = task.getIsComplete();
        this.ownerId = task.getOwner().getId();
        this.status = task.getStatus();
        this.time = task.getTime();

        if( task.getSharedList() != null ) {
            this.shareHolders = task.getSharedList().stream().map(ShareHolderDto::new).toList();
        }

        if( task.getTags() != null )
            this.tags = task.getTags().stream().map(TagEntity::getName).toList();
    }
}
