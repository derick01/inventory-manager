/**
 * File: ProductServiceTest.java
 * Description: Contains test code for ProductService
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.inventorymanager.service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

import com.github.derick01.inventorymanager.repository.ProductRepository;
import com.github.derick01.inventorymanager.dtos.ProductDTO;
import com.github.derick01.inventorymanager.entity.Product;
import com.github.derick01.inventorymanager.mapper.ProductMapper;
import com.github.derick01.inventorymanager.exception.ResourceNotFoundException;
import com.github.derick01.inventorymanager.enums.Category;
import com.github.derick01.inventorymanager.enums.Unit;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product testProduct = new Product();
    private ProductDTO testProductDto = new ProductDTO();

    private final String productUuidString = "12345678-1234-4321-8765-4321abcdef12";
    private final UUID productUuid = UUID.fromString(productUuidString);

    @BeforeEach
    void setup() {
        testProduct.setId(1L);
        testProduct.setExternalId(productUuid);
        testProduct.setName("Test Product");
        testProduct.setSku("TEST-SKU");
        testProduct.setBrand("Test Brand");
        testProduct.setPrice(BigDecimal.ONE);
        testProduct.setCategory(Category.CUSTOM_CATEGORY_1);
        testProduct.setLowThreshold(BigDecimal.valueOf(5));
        testProduct.setUnit(Unit.CUSTOM_UNIT_1);


        testProductDto.setExternalId(productUuid);
        testProductDto.setName("Test Product");
        testProductDto.setSku("TEST-SKU");
        testProductDto.setBrand("Test Brand");
        testProductDto.setPrice(BigDecimal.ONE);
        testProductDto.setCategory(Category.CUSTOM_CATEGORY_1);
        testProductDto.setLowThreshold(BigDecimal.valueOf(5));
        testProductDto.setUnit(Unit.CUSTOM_UNIT_1);
    }

    @Test
    @DisplayName("Should find product by external id")
    void shouldFindProductById() {
        // Given
        
        when(productRepository.findByExternalId(productUuid))
            .thenReturn(Optional.of(testProduct));
        when(productMapper.toDto(testProduct)).thenReturn(testProductDto);
        
        // When
        ProductDTO result = productService.getProductByExternalId(productUuid);

        // Then
        assertNotNull(result);
        assertEquals(productUuid, result.getExternalId());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when product is not found")
    void shouldThrowResourceNotFoundExceptionWhenProductNotFound() {
        // Given
        final String unknownProductUuidString = "12345678-1234-4321-8765-abcdefabcdef";
        final UUID unknownProductUuid = UUID.fromString(unknownProductUuidString);

        when(productRepository.findByExternalId(unknownProductUuid))
            .thenReturn(Optional.empty());

        // When & Then
        final ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> productService.getProductByExternalId(unknownProductUuid));
        
        assertEquals(String.format("Product with external id %s not found",
                        unknownProductUuid.toString()),
                    exception.getMessage());
    }
}