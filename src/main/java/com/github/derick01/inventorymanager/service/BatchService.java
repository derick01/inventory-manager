/**
 * File: BatchService.java
 * Description: Contains business logic related to batches
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.inventorymanager.service;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.derick01.inventorymanager.repository.BatchRepository;
import com.github.derick01.inventorymanager.dtos.BatchDTO;
import com.github.derick01.inventorymanager.entity.Batch;
import com.github.derick01.inventorymanager.mapper.BatchMapper;

import com.github.derick01.inventorymanager.repository.ProductRepository;
import com.github.derick01.inventorymanager.entity.Product;
import com.github.derick01.inventorymanager.exception.ResourceNotFoundException;

@Service
public class BatchService {

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BatchMapper batchMapper;

    @Transactional(readOnly = true)
    public List<BatchDTO> findAll() {
        List<Batch> batches = batchRepository.findAll();
        List<BatchDTO> batchesAsDtoList = batchMapper.toDtoList(batches);
        return batchesAsDtoList;
    }

    @Transactional(readOnly = true)
    public BatchDTO getBatchByExternalId(UUID externalId) {
        Batch batch = batchRepository.findByExternalId(externalId)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                            "Batch not found"));
        BatchDTO batchAsDTO  = batchMapper.toDto(batch);
        return batchAsDTO;
    }
    
    @Transactional
    public BatchDTO create(BatchDTO batchDto) {
        UUID productExternalId = batchDto.getProductExternalId();
        Product product = productRepository
                            .findByExternalId(productExternalId)
                            .orElseThrow(() 
                                    -> new ResourceNotFoundException(
                                        "Product not found"));

        Batch batch = batchMapper.toEntity(batchDto);
        batch.setProduct(product);
        batch = batchRepository.save(batch);
        return batchMapper.toDto(batch);
    }

    @Transactional
    public BatchDTO updateBatchByExternalId(UUID externalId,
                                            BatchDTO updatedBatchDto) {
        Batch existingBatch = batchRepository
                                    .findByExternalId(externalId)
                                    .orElseThrow(() 
                                            -> new ResourceNotFoundException(
                                                "Batch not found"));

        batchMapper.updateBatchFromDto(updatedBatchDto, existingBatch);

        UUID updatedBatchDtoProductExternalId = updatedBatchDto
                                                    .getProductExternalId();

        UUID existingBatchProductExternalId = (existingBatch
                                                    .getProduct() != null) ?
                                               existingBatch
                                                    .getProduct()
                                                    .getExternalId() :
                                               null;

        if (!Objects.equals(updatedBatchDtoProductExternalId,
                           existingBatchProductExternalId)) {
            Product product = productRepository
                            .findByExternalId(updatedBatchDtoProductExternalId)
                            .orElseThrow(() 
                                    -> new ResourceNotFoundException(
                                        "Product not found"));
            existingBatch.setProduct(product);
        }

        existingBatch = batchRepository.save(existingBatch);

        return batchMapper.toDto(existingBatch);
    }

    @Transactional
    public void deleteBatchByExternalId(UUID externalId) {
        Batch batch = batchRepository.findByExternalId(externalId)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                            "Batch not found"));
        batchRepository.delete(batch);
    }
}