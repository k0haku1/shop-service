package com.example.shop_service.service;

import com.example.shop_service.persistence.models.Product;
import com.example.shop_service.persistence.repository.ProductRepository;
import com.example.shop_service.service.dto.ProductDTO;
import com.example.shop_service.service.dto.ProductSaveDTO;
import com.example.shop_service.service.impl.ProductServiceImpl;
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
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepositoryMock;

    @Mock
    private ConversionService conversionServiceMock;

    @InjectMocks
    private ProductServiceImpl underTest;

    @Test
    public void getProductById() {

        final UUID ID = UUID.randomUUID();
        final Product product = Product.builder()
                .id(ID)
                .name("test name")
                .price(BigDecimal.valueOf(100))
                .category("category")
                .description("desc")
                .createdAt(LocalDate.now())
                .quantity(111)
                .article("test arc1")
                .build();

        when(productRepositoryMock.findById(ID)).thenReturn(Optional.ofNullable(product));

        when(conversionServiceMock.convert(product, ProductDTO.class)).thenReturn(new ProductDTO(
                product.getId(),
                product.getName(),
                product.getArticle(),
                product.getPrice(),
                product.getQuantity(),
                product.getDescription(),
                product.getCategory(),
                product.getCreatedAt())
        );

        ProductDTO result = underTest.getProductById(ID);

        assertThat(result)
                .isNotNull()
                .satisfies(res -> {
                        assertThat(res.getName()).isEqualTo(product.getName());
                });
        //Проверка поиска товара по ID
        verify(productRepositoryMock).findById(ID);
        //Проверка конвертации Product -> ProductDTO
        verify(conversionServiceMock).convert(product, ProductDTO.class);

    }

    @Test
    public void saveProduct () {

        final ProductSaveDTO productSaveDTO = ProductSaveDTO.builder()
                .name("Test Product")
                .article("unique-article")
                .price(BigDecimal.valueOf(100))
                .quantity(10)
                .description("Test Description")
                .category("Test Category")
                .build();

        final Product product = Product.builder()
                .id(UUID.randomUUID())
                .name("Test Product")
                .article("unique-article")
                .price(BigDecimal.valueOf(100))
                .quantity(10)
                .description("Test Description")
                .category("Test Category")
                .build();

        final ProductDTO productDTO = new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getArticle(),
                product.getCategory(),
                LocalDate.now()
        );

        when(productRepositoryMock.existsByArticle(productSaveDTO.getArticle())).thenReturn(false);

        when(conversionServiceMock.convert(productSaveDTO, Product.class)).thenReturn(product);

        when(productRepositoryMock.save(product)).thenReturn(product);

        when(conversionServiceMock.convert(product, ProductDTO.class)).thenReturn(productDTO);

        ProductDTO result = underTest.saveProduct(productSaveDTO);

        assertThat(result)
                .isNotNull()
                .satisfies(res -> {
                    assertThat(res.getId()).isEqualTo(product.getId());
                    assertThat(res.getName()).isEqualTo(product.getName());
                });

        // Проверка наличия продукта в репозитории
        verify(productRepositoryMock).existsByArticle(productSaveDTO.getArticle());

        // Проверка конвертации ProductSaveDTO -> Product
        verify(conversionServiceMock).convert(productSaveDTO, Product.class);

        // Сохранение продукта
        verify(productRepositoryMock).save(product);

        // Проверка конвертации Product -> ProductDTO
        verify(conversionServiceMock).convert(product, ProductDTO.class);

    }

    @Test
    public void getAllProducts() {

        Product product1 = new Product(
                UUID.randomUUID(),
                "Product 1",
                "article 1",
                "description 1",
                "category 1",
                BigDecimal.valueOf(100),
                10,
                LocalDate.now()
        );

        Product product2 = new Product(

                UUID.randomUUID(),
                "Product 2",
                "article 2",
                "description 2",
                "category 2",
                BigDecimal.valueOf(100),
                10,
                LocalDate.now()
        );

        Page <Product> productPage = new PageImpl<>(List.of(product1, product2));
        Pageable pageable = PageRequest.of(0,2);

        when(productRepositoryMock.findAll(pageable)).thenReturn(productPage);

        Page <ProductDTO> result = underTest.getAllProducts(pageable);

        assertThat (result)
                .isNotNull()
                .hasSize(2)
                .satisfies(page -> assertThat(result.getTotalElements()).isEqualTo(2));

        //Проверка возвращаемого методом результата
        verify(productRepositoryMock).findAll(pageable);
    }

}