package com.example.shop_service.service;
import com.example.shop_service.service.dto.ProductDTO;
import com.example.shop_service.service.dto.ProductSaveDTO;
import com.example.shop_service.service.dto.ProductUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {

    Page<ProductDTO> getAllProducts(Pageable pageable);

    ProductDTO getProductById(UUID id);

    ProductDTO getProductByIdCurrency (UUID id);

    ProductDTO saveProduct(ProductSaveDTO productDTO);

    ProductDTO updateProduct(ProductUpdateDTO product, UUID id);

    void deleteProduct(UUID id);

}
