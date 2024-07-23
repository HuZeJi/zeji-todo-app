package com.huzeji.todomanager.svc;

import com.huzeji.model.dto.AuditLogDto;
import org.javers.core.metamodel.object.CdoSnapshot;

import java.util.List;

public interface AuditSvc {
    List<AuditLogDto> getAuditLog();
}
