/**
 * File: ProductMapper.java
 * Description: Contains interface for converting between product DTO
 *              and product entity
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.pantry_tracker.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.github.derick01.pantry_tracker.dtos.ProductDTO;
import com.github.derick01.pantry_tracker.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDto(Product product);

    List<ProductDTO> toDtoList(List<Product> productList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "batches", ignore = true)
    Product toEntity(ProductDTO productDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "batches", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateProductFromDto(ProductDTO dto, @MappingTarget Product entity);
}