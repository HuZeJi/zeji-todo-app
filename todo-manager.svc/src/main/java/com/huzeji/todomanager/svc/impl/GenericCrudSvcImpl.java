package com.huzeji.todomanager.svc.impl;

import com.huzeji.todomanager.repository.generic.GenericRepository;
import com.huzeji.todomanager.svc.GenericCrudSvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public abstract class GenericCrudSvcImpl<T, I> implements GenericCrudSvc<T, I> {

    private static final String PAGE_INDEX = "pageIndex";
    private static final String PAGE_SIZE = "pageSize";

    public abstract GenericRepository<T, I> getRepository();

    @Override
    public T create( T registry ) {
        return getRepository().save( registry );
    }

//    @Override
//    @Deprecated
//    public void delete( I identifier ) {
//        T registry = getRepository().findById( identifier ).orElseThrow(NoSuchElementException::new);
//        getRepository().delete( registry );
//    }

//    @Override
//    @Deprecated
//    public T update( T registry, I identifier ) {
//        getRepository().findById( identifier ).orElseThrow(NoSuchElementException::new);
//        return create( registry );
//    }

    @Override
    public Page<T> get(Map<String, Object> filters ) {
        Pageable pageRequest = buildPage( filters );
        return getRepository().getPage( filters, pageRequest );
    }

    private Pageable buildPage( Map<String, Object> filters ) {
        int pageIndex = Integer.parseInt( ( String ) Optional.ofNullable( filters.remove( PAGE_INDEX ) ).orElseGet( () -> "0" ));
        int pageSize = Integer.parseInt( ( String ) Optional.ofNullable( filters.remove( PAGE_SIZE ) ).orElseGet( () -> "10" ));
        return PageRequest.of( pageIndex, pageSize );
    }
}
