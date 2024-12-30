package com.example.shop_service.service.impl;

import com.example.shop_service.controller.dto.ProductFilterDto;
import com.example.shop_service.exception.ProductAlreadyExist;
import com.example.shop_service.persistence.models.Product;
import com.example.shop_service.persistence.repository.ProductRepository;
import com.example.shop_service.service.ProductService;
import com.example.shop_service.service.dto.ProductDTO;
import com.example.shop_service.service.dto.ProductSaveDTO;
import com.example.shop_service.service.dto.ProductUpdateDTO;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ConversionService conversionService;

    @Override
    public Page<ProductDTO> searchProducts(ProductFilterDto filter, Pageable pageable) {
        final Specification<Product> specification = (root, query, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.getName() + "%"));
            }
            if (filter.getPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getPrice()));
            }
            if (filter.getQuantity() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), filter.getQuantity()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<Product> productsPage = productRepository.findAll(specification, pageable);

        return productsPage.map(product -> conversionService.convert(product, ProductDTO.class));
    }


    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(product -> new ProductDTO(
                product.getId(),
                product.getName(),
                product.getArticle(),
                product.getPrice(),
                product.getQuantity(),
                product.getDescription(),
                product.getCategory(),
                product.getCreatedAt())
        );
    }

    @Override
    public ProductDTO getProductById(UUID id) {
        final Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        return conversionService.convert(product, ProductDTO.class);
    }

    @Override
    public ProductDTO saveProduct(ProductSaveDTO productDTO) {

        if (productRepository.existsByArticle(productDTO.getArticle())) {
            throw new ProductAlreadyExist(productDTO.getArticle());
        }

        final Product product = conversionService.convert(productDTO, Product.class);
        return conversionService.convert(productRepository.save(product), ProductDTO.class);

    }

    @Override
    public ProductDTO updateProduct(ProductUpdateDTO productUpdate, UUID id) {

        final ProductDTO productDTO = getProductById(id);
        final Product product = conversionService.convert(productDTO, Product.class);

        if (productUpdate.getArticle() != null && !productUpdate.getArticle().equals(product.getArticle())) {
            if (productRepository.existsByArticle(product.getArticle())) {
                throw new ProductAlreadyExist(product.getArticle());
            }
            product.setArticle(productUpdate.getArticle());
        }if (productUpdate.getName() != null && !productUpdate.getName().equals(product.getName())) {
            product.setName(productUpdate.getName());
        }if (productUpdate.getPrice() != null && !productUpdate.getPrice().equals(product.getPrice())) {
            product.setPrice(productUpdate.getPrice());
        }if (productUpdate.getDescription() != null && !productUpdate.getDescription().equals(product.getDescription())) {
            product.setDescription(productUpdate.getDescription());
        }if(productUpdate.getCategory() != null && !productUpdate.getCategory().equals(product.getCategory())) {
            product.setCategory(productUpdate.getCategory());
        }if (productUpdate.getQuantity() != null && !productUpdate.getQuantity().equals(product.getQuantity())) {
            product.setQuantity(productUpdate.getQuantity());
        }

        return conversionService.convert(productRepository.save(product), ProductDTO.class);

    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
