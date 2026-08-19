/**
 * File: Batch.java
 * Description: Contains the implementation for the Batch entity
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.inventorymanager.entity;

import java.util.UUID;
import java.time.LocalDate;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import org.hibernate.annotations.UuidGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.github.derick01.inventorymanager.enums.Unit;
import com.github.derick01.inventorymanager.enums.ShelfLocation;

@Entity
@Table(name = "batches")
@Getter
@Setter
@NoArgsConstructor
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    private UUID externalId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private BigDecimal quantity;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    private LocalDate expiryDate;
    
    @Enumerated(EnumType.STRING)
    private ShelfLocation shelfLocation;
}