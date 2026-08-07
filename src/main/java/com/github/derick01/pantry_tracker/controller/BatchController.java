/**
 * File: BatchController.java
 * Description: Contains the Batch Rest controller
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.pantry_tracker.controller;

import java.util.UUID;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.derick01.pantry_tracker.service.BatchService;
import com.github.derick01.pantry_tracker.dtos.BatchDTO;
import com.github.derick01.pantry_tracker.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1/batches")
public class BatchController {
    
    @Autowired
    private BatchService batchService;

    // GET: Returns 200 Ok + list of DTOs
    @GetMapping
    public ResponseEntity<List<BatchDTO>> getAllBatches() {
        List<BatchDTO> allBatches = batchService.findAll();
        return ResponseEntity.ok(allBatches);
    }

    // GET: Returns 200 Ok + retrieved DTO
    @GetMapping("/{id}")
    public ResponseEntity<BatchDTO> getBatch(@PathVariable UUID id) {
        BatchDTO retrievedBatch = batchService.getBatchByExternalId(id);
        return ResponseEntity.ok(retrievedBatch);
    }

    // POST: Returns 201 Created + new DTO
    @PostMapping
    public ResponseEntity<BatchDTO> createBatch(@RequestBody BatchDTO batch) {
        BatchDTO createdBatch = batchService.create(batch);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBatch);
    }

    // PUT: Returns 200 Ok + updated DTO
    @PutMapping("/{id}")
    public ResponseEntity<BatchDTO> updateBatch(@PathVariable UUID id,
                                    @RequestBody BatchDTO batch) {
        BatchDTO updatedBatch = batchService.updateBatchByExternalId(id, batch);
        return ResponseEntity.ok(updatedBatch);
    }

    // DELETE: Returns 204 if success, 404 if not found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBatch(@PathVariable UUID id) {

        try {
            batchService.deleteBatchByExternalId(id);
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            throw e;
        }

        return ResponseEntity.noContent().build();
    }
}