package com.huzeji.todomanager.svc.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public class PageUtil {
    private static final String PAGE_INDEX = "pageIndex";
    private static final String PAGE_SIZE = "pageSize";
    public static Pageable buildPageRequest(Map<String, Object> filters ) {
        int pageIndex = Integer.parseInt((String) Optional.ofNullable(filters.remove(PAGE_INDEX)).orElseGet(() -> "0"));
        int pageSize = Integer.parseInt((String) Optional.ofNullable(filters.remove(PAGE_SIZE)).orElseGet(() -> "10"));
        return PageRequest.of(pageIndex, pageSize);
    }

    private PageUtil() throws IllegalAccessException { throw new IllegalAccessException(); }
}
