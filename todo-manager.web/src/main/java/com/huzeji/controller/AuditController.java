package com.huzeji.controller;

import com.huzeji.model.dto.AuditLogDto;
import com.huzeji.todomanager.svc.AuditSvc;
import io.sentry.Sentry;
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

    @GetMapping( "sentry" )
    public void testSentry() {
        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }
    }
}
