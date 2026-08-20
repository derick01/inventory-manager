/**
 * File: ProductRepositoryTest.java
 * Description: Contains test code for ProductRepository
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.inventorymanager.repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.beans.factory.annotation.Autowired;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.derick01.inventorymanager.entity.Product;
import com.github.derick01.inventorymanager.entity.Batch;
import com.github.derick01.inventorymanager.exception.ResourceNotFoundException;
import com.github.derick01.inventorymanager.enums.ShelfLocation;
import com.github.derick01.inventorymanager.enums.Category;
import com.github.derick01.inventorymanager.enums.Unit;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17-alpine");

    @Autowired
    private ProductRepository productRepository;

    private Product product1;
    private Batch product1Batch1;
    private Batch product1Batch2;

    @BeforeEach
    void setup() {
        product1 = new Product();
        product1.setName("Test Product 1");
        product1.setSku("TEST-SKU");
        product1.setBrand("Test Brand");
        product1.setPrice(BigDecimal.ONE);
        product1.setCategory(Category.CUSTOM_CATEGORY_1);
        product1.setLowThreshold(BigDecimal.valueOf(5));
        product1.setUnit(Unit.CUSTOM_UNIT_1);

        product1Batch1 = new Batch();
        product1Batch1.setQuantity(BigDecimal.valueOf(2));
        product1Batch1.setUnit(Unit.CUSTOM_UNIT_1);
        product1Batch1.setExpiryDate(LocalDate.now().plusDays(5));
        product1Batch1.setShelfLocation(ShelfLocation.CUSTOM_SHELFLOCATION_1);
        product1.addBatch(product1Batch1);

        product1Batch2 = new Batch();
        product1Batch2.setQuantity(BigDecimal.valueOf(2));
        product1Batch2.setUnit(Unit.CUSTOM_UNIT_1);
        product1Batch2.setExpiryDate(LocalDate.now().plusDays(2));
        product1Batch2.setShelfLocation(ShelfLocation.CUSTOM_SHELFLOCATION_1);
        product1.addBatch(product1Batch2);

        product1 = productRepository.save(product1);
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Should find low stock products")
    void shouldFindLowStockProducts() {
        // Given

        // When
        List<Product> lowStock = productRepository.findProductsWithLowStock();

        // Then
        assertEquals(1, lowStock.size());
        assertEquals("Test Product 1", lowStock.get(0).getName());
    }

    @Test
    @DisplayName("Should find empty low stock products")
    void shouldFindEmptyLowStockProducts() {
        //Given
        Batch product1Batch3 = new Batch();
        product1Batch3.setQuantity(BigDecimal.valueOf(10));
        product1Batch3.setUnit(Unit.CUSTOM_UNIT_1);
        product1Batch3.setExpiryDate(LocalDate.now().plusDays(2));
        product1Batch3.setShelfLocation(ShelfLocation.CUSTOM_SHELFLOCATION_1);
        product1.addBatch(product1Batch3);

        // product1 = productRepository.save(product1);

        // When
        List<Product> lowStock = productRepository.findProductsWithLowStock();

        // Then
        assertEquals(0, lowStock.size());
    }
}