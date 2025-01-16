package com.example.shop_service.service.strategy;

import com.example.shop_service.service.dto.SearchFilterDto;
import com.example.shop_service.service.type.SearchOperation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class SearchFilterString implements SearchFilterDto<String> {

    private String field;
    private String value;
    private SearchOperation operation;

    @Override
    public Predicate buildPredicate(CriteriaBuilder cb, Root<?> root) {
        switch (getOperation()) {
            case EQUAL:
                return cb.equal(root.get(getField()), getValue());
            case LIKE:
                return cb.like(root.get(getField()), "%" + getValue() + "%");
            default:
                throw new UnsupportedOperationException("Operation not supported: ");
        }
    }
}
