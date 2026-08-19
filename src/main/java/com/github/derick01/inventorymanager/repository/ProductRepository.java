/**
 * File: ProductRepository.java
 * Description: Contains product repository code
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.inventorymanager.repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.github.derick01.inventorymanager.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByExternalId(UUID externalId);

    @Query("SELECT p " +
           "FROM Products p " +
           "LEFT JOIN p.batches b " +
           "GROUP BY p.id " +
           "HAVING COALESCE(SUM(b.quantity), 0) < p.lowThreshold")
    List<Product> findProductsWithLowStock();
}