package com.huzeji.todomanager.repository.generic.specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface GenericSpecs<T> {
    public default Specification<T> filter(Map<String, Object> filters) {
        return ( root, query, builder ) -> {
            List<Predicate> predicates = filters
                    .entrySet()
                    .stream()
                    .map( filter -> builder.equal( root.get( filter.getKey() ), filter.getValue() ) )
                    .toList();
            return builder.and( predicates.toArray( new Predicate[0] ) );
        };
    }
}
