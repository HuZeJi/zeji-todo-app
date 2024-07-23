package com.huzeji.todomanager.svc.impl;

import com.huzeji.model.dto.AuditLogDto;
import com.huzeji.todomanager.svc.AuditSvc;
import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditSvcImpl implements AuditSvc {
    @Autowired private Javers javers;
    @Override
    public List<AuditLogDto> getAuditLog() {
        return javers.findSnapshots( QueryBuilder.anyDomainObject().build() )
                .stream()
                .map( AuditLogDto::new )
                .collect( Collectors.toList() );
    }
}
