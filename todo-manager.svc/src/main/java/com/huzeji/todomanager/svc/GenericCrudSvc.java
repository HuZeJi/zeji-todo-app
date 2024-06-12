package com.huzeji.todomanager.svc;

import org.springframework.data.domain.Page;

import java.util.Map;

public interface GenericCrudSvc<T, I> {
    public T create( T registry );
    public Page<T> get(Map<String, Object> filters );
    public void delete( I identifier );
    public T update( T registry, I identifier );
}
