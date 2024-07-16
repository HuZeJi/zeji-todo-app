package com.huzeji.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.huzeji.model.dto.TaskDto;
import com.huzeji.model.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "task")
@Data
@NoArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private Boolean isComplete = Boolean.FALSE;

    @ManyToOne
    @JoinColumn( name = "user_id", nullable = false )
    @JsonBackReference( value = "user-task" )
    private UserEntity owner;

    private StatusEnum status = StatusEnum.ACTIVE;
    private LocalDateTime time = LocalDateTime.now();
    @OneToMany( mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JsonManagedReference( value = "task-tag" )
    List<TagEntity> tags;

    @OneToMany( mappedBy = "shared", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JsonManagedReference( value = "user_task-shared" )
    List<UserTaskEntity> sharedList;

    public TaskEntity(TaskDto task, UserEntity user) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        if( task.getIsComplete() != null ) this.isComplete = task.getIsComplete();
        this.owner = user;
        if( task.getStatus() != null ) this.status = task.getStatus();
        if( task.getTime() != null ) this.time = task.getTime();
    }
}
