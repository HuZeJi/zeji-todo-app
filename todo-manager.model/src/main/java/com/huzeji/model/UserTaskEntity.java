package com.huzeji.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.config.Task;

@Entity( name = "user-task" )
@Data
@NoArgsConstructor
public class UserTaskEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn( name = "shareholder_id", nullable = false )
    @JsonBackReference( value = "user_task-shareholder" )
    private UserEntity shareholder;
    @ManyToOne
    @JoinColumn( name = "shared_id", nullable = false )
    @JsonBackReference( value = "user_task-shared" )
    private TaskEntity shared;

    public UserTaskEntity( UserEntity user, TaskEntity task ) {
        this.shareholder = user;
        this.shared = task;
    }
}
