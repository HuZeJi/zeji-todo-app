package com.huzeji.model.dto;

import com.huzeji.model.UserEntity;
import com.huzeji.model.UserTaskEntity;
import com.huzeji.model.enums.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShareHolderDto {
    private Long id;
    private String name;
    private String nickname;
    private StatusEnum status;

    public ShareHolderDto( UserEntity user ) {
        this.id = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.status = user.getStatus();
    }

    public ShareHolderDto(UserTaskEntity userTask) {
        this.id = userTask.getShareholder().getId();
        this.name = userTask.getShareholder().getName();
        this.nickname = userTask.getShareholder().getNickname();
        this.status = userTask.getShareholder().getStatus();
    }
}
