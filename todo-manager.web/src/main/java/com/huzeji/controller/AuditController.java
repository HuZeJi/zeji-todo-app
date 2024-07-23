package com.huzeji.controller;

import com.huzeji.model.dto.AuditLogDto;
import com.huzeji.model.dto.TaskDto;
import com.huzeji.todomanager.svc.AuditSvc;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping( "/public/api/v1/audit" )
public class AuditController {
    @Autowired private AuditSvc auditSvc;
    @GetMapping()
    public List<AuditLogDto> getAll() {
        return auditSvc.getAuditLog();
    }
}
