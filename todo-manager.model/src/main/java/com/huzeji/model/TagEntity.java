package com.huzeji.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.huzeji.model.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tag")
@Data
@NoArgsConstructor
public class TagEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private StatusEnum status;
    @ManyToOne
    @JoinColumn( name = "task_id", nullable = false )
    @JsonBackReference
    private TaskEntity task;

    public TagEntity(String name, TaskEntity task) {
        this.name = name;
        this.task = task;
        this.status = StatusEnum.ACTIVE;
    }
}
