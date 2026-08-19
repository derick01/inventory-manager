/**
 * File: ProductDTO.java
 * Description: Contains the Product DTO
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.inventorymanager.dtos;

import java.util.UUID;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import com.github.derick01.inventorymanager.enums.Category;
import com.github.derick01.inventorymanager.enums.Unit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductDTO {
    private UUID externalId;

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "SKU is required")
    private String sku;

    private String brand;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Category is required")
    private Category category;

    @Positive(message = "Low quantity threshold must be greater than zero")
    private BigDecimal lowThreshold;

    @NotNull(message = "Unit is required")
    private Unit unit;
}
