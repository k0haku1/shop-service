package com.example.shop_service.service.strategy;

import com.example.shop_service.service.dto.SearchFilterDto;
import com.example.shop_service.service.type.SearchOperation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SearchFilterData implements SearchFilterDto <LocalDate> {

    private String field;
    private LocalDate value;
    private SearchOperation operation;

    @Override
    public Predicate buildPredicate (CriteriaBuilder cb, Root<?> root) {

        switch (getOperation()) {
            case EQUAL:
                return cb.equal(root.get(getField()), getValue());
            case GRATER_THAN_OR_EQ:
                return cb.greaterThanOrEqualTo(root.get(getField()), getValue());
            case LESS_THAN_OR_EQ:
                return cb.lessThanOrEqualTo(root.get(getField()), getValue());
            case LIKE:
                return cb.between(root.get(getField()), getValue().minusDays(3), getValue().plusDays(3));
            default:
                throw new UnsupportedOperationException("Operation not supported");

        }
    }

}
