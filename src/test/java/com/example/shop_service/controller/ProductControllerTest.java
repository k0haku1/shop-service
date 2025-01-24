
/*
package com.example.shop_service.controller;

import com.example.shop_service.controller.dto.ControllerSaveDTO;
import com.example.shop_service.service.ProductService;
import com.example.shop_service.service.dto.ProductDTO;
import com.example.shop_service.service.dto.ProductSaveDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController underTest;

    @Mock
    private ProductService productServiceMock;

    @Mock
    private ConversionService conversionServiceMock;


    @Test
    void getProductById(){
        UUID productId = UUID.randomUUID();
        ProductDTO productDTO = new ProductDTO(
                productId,
                "Product Name",
                "description",
                BigDecimal.valueOf(100),
                10,
                "article",
                "category",
                LocalDate.now()
        );

        when(productServiceMock.getProductById(productId)).thenReturn(productDTO);

        ResponseEntity<ProductDTO> response = underTest.getProductById(productId);
        
        assertThat(response)
                .isNotNull()
                .satisfies(res -> {
                    assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(res.getBody()).isNotNull();
                    assertThat(res.getBody()).isEqualTo(productDTO);
                });

        verify(productServiceMock, times(1)).getProductById(productId);
    }

    @Test
    void saveProduct(){
        ControllerSaveDTO controllerSaveDTO = ControllerSaveDTO.builder()
                .name("Product Name")
                .description("description")
                .article("article")
                .price(BigDecimal.valueOf(100))
                .quantity(10)
                .category("category")
                .build();

        ProductSaveDTO productSaveDTO = new ProductSaveDTO(
                "Product Name",
                "description",
                "article",
                BigDecimal.valueOf(100),
                10,
                "category"
        );

        ProductDTO productDTO = new ProductDTO(
                UUID.randomUUID(),
                "Product Name",
                "description",
                BigDecimal.valueOf(100),
                10,
                "article",
                "category",
                LocalDate.now()
        );

        when(conversionServiceMock.convert(controllerSaveDTO, ProductSaveDTO.class)).thenReturn(productSaveDTO);
        when(productServiceMock.saveProduct(productSaveDTO)).thenReturn(productDTO);

        ProductDTO response = underTest.saveProduct(controllerSaveDTO);

        assertThat(response)
                .isNotNull()
                .isEqualTo(productDTO);

        verify(conversionServiceMock, times(1)).convert(controllerSaveDTO, ProductSaveDTO.class);
        verify(productServiceMock, times(1)).saveProduct(productSaveDTO);
    }

    @Test
    void getAllProduct () {

        ProductDTO product1 = new ProductDTO(UUID.randomUUID(), "Product 1", "description 1", BigDecimal.valueOf(100), 10, "article 1", "category 1", LocalDate.now());
        ProductDTO product2 = new ProductDTO(UUID.randomUUID(), "Product 2", "description 2", BigDecimal.valueOf(100), 10, "article 2", "category 2", LocalDate.now());

        Page<ProductDTO> ProductPage = new PageImpl<>(List.of(product1, product2));
        Pageable pageable = PageRequest.of(0 , 2);

        when(productServiceMock.getAllProducts(pageable)).thenReturn(ProductPage);

        Page<ProductDTO> response = underTest.getAllProducts(pageable);

        assertThat(response)
                .isNotNull()
                .hasSize(2)
                .extracting(ProductDTO::getName)
                .containsExactly("Product 1", "Product 2");

        verify(productServiceMock, times(1)).getAllProducts(any(Pageable.class));
    }
}
*/