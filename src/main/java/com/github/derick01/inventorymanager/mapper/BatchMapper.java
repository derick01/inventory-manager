/**
 * File: BatchMapper.java
 * Description: Contains interface for converting between batch DTO
 *              and batch entity
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.inventorymanager.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.github.derick01.inventorymanager.dtos.BatchDTO;
import com.github.derick01.inventorymanager.entity.Batch;

@Mapper(componentModel = "spring")
public interface BatchMapper {

    @Mapping(target = "productExternalId", source = "product.externalId")
    BatchDTO toDto(Batch batch);

    List<BatchDTO> toDtoList(List<Batch> batchList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    Batch toEntity(BatchDTO batchDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Batch updateBatchFromDto(BatchDTO dto, @MappingTarget Batch entity);
}