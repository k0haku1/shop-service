package com.example.shop_service.service.dto;


import com.example.shop_service.service.strategy.SearchFilterData;
import com.example.shop_service.service.strategy.SearchFilterInt;
import com.example.shop_service.service.strategy.SearchFilterPrice;
import com.example.shop_service.service.strategy.SearchFilterString;
import com.example.shop_service.service.type.SearchOperation;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true , property = "field")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SearchFilterPrice.class, name = "price"),
        @JsonSubTypes.Type(value = SearchFilterString.class, name = "name"),
        @JsonSubTypes.Type(value = SearchFilterData.class, name = "createdAt"),
        @JsonSubTypes.Type(value = SearchFilterInt.class, name = "quantity")
})

public interface SearchFilterDto <T>  {

    Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<?> root);

    String getField();
    T getValue();
    SearchOperation getOperation();

}
