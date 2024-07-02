package com.huzeji.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.huzeji.model.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "todo_user")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String nickname;
    private String password;
    private StatusEnum status;
    @OneToMany( mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JsonManagedReference
    private List<TaskEntity> tasks;
}
