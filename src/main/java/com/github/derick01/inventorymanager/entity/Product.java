/**
 * File: Product.java
 * Description: Contains the implementation for the Product entity
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.inventorymanager.entity;

import java.util.UUID;
import java.util.List;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import org.hibernate.annotations.UuidGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.github.derick01.inventorymanager.enums.Category;
import com.github.derick01.inventorymanager.enums.Unit;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    private UUID externalId;
    
    private String name;
    private String sku;
    private String brand;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private Category category;

    private BigDecimal lowThreshold;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Batch> batches;
}