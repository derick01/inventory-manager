/**
 * File: ProductRepository.java
 * Description: Contains product repository code
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.pantry_tracker.repository;

import java.util.UUID;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.derick01.pantry_tracker.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByExternalId(UUID externalId);
}