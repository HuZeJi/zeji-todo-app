package com.huzeji.controller;

import com.huzeji.todomanager.svc.GenericCrudSvc;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

abstract class GenericRest<T, I> {
    public abstract GenericCrudSvc<T, I> getService();
    @GetMapping()
    public Page<T> get( @RequestParam Map<String, Object> filters ) { return getService().get( filters ); }

    @PostMapping()
    public T create( @RequestBody T registry ) { return getService().create( registry ); }

    @PutMapping( "/{id}" )
    public T update( @RequestBody T registry, @PathVariable( "id" ) I identifier ) {
        return getService().update( registry, identifier );
    }

    @DeleteMapping( "/{id}" )
    public void delete( @PathVariable( "id" ) I identifier ) {
        getService().delete( identifier );
    }
}
