package com.example.shop_service.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import com.example.shop_service.controller.dto.ControllerSaveDTO;
import com.example.shop_service.controller.dto.ControllerUpdateDTO;
import com.example.shop_service.service.dto.ProductDTO;
import com.example.shop_service.service.ProductService;
import com.example.shop_service.service.dto.ProductSaveDTO;
import com.example.shop_service.service.dto.ProductUpdateDTO;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ConversionService conversionService;

    @GetMapping
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductByIdCurrency(id));
    }

    @PostMapping("save")
    public ProductDTO saveProduct(@RequestBody ControllerSaveDTO controllerSaveDTO) {
        return productService.saveProduct(conversionService.convert(controllerSaveDTO, ProductSaveDTO.class));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ControllerUpdateDTO controllerUpdateDTO, @PathVariable UUID id) {
        return ResponseEntity.ok(productService.updateProduct(conversionService.convert(controllerUpdateDTO, ProductUpdateDTO.class), id));
    }

    @DeleteMapping("delete/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }


}
