package com.huzeji.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.javers.core.metamodel.object.CdoSnapshot;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class AuditLogDto {
    private String entity;
    private String changeType;
    private LocalDateTime commitTimestamp;
    private String author;
    private Map<String, Object> changes;

    public AuditLogDto(CdoSnapshot snapshot) {
        this.entity = snapshot.getGlobalId().getTypeName();
        this.changeType = snapshot.getType().name();
        this.commitTimestamp = snapshot.getCommitMetadata().getCommitDate();
        this.author = snapshot.getCommitMetadata().getAuthor();
        this.changes = new HashMap<>();
        snapshot.getState().forEachProperty(this.changes::put);
    }
}
