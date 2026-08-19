/**
 * File: BatchDTO.java
 * Description: Contains the Batch DTO
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.inventorymanager.dtos;

import java.util.UUID;
import java.time.LocalDate;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Future;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import com.github.derick01.inventorymanager.enums.Unit;
import com.github.derick01.inventorymanager.enums.ShelfLocation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BatchDTO {
    private UUID externalId;

    @NotNull(message = "Product Id is required")
    private UUID productExternalId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    private BigDecimal quantity;

    @NotNull(message = "Unit is required")
    private Unit unit;
    
    @Future(message = "Future date is required")
    private LocalDate expiryDate;
    
    @NotNull(message = "ShelfLocation is required")
    private ShelfLocation shelfLocation;
}
