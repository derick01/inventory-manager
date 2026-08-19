/**
 * File: ProductController.java
 * Description: Contains the Product Rest controller
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.inventorymanager.controller;

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

import com.github.derick01.inventorymanager.service.ProductService;
import com.github.derick01.inventorymanager.dtos.ProductDTO;
import com.github.derick01.inventorymanager.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    // GET: Returns 200 Ok + list of DTOs
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> allProducts = productService.findAll();
        return ResponseEntity.ok(allProducts);
    }

    // GET: Returns 200 Ok + retrieved DTO
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable UUID id) {
        ProductDTO retrievedProduct = productService.getProductByExternalId(id);
        return ResponseEntity.ok(retrievedProduct);
    }

    // POST: Returns 201 Created + new DTO
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product) {
        ProductDTO createdProduct = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    // PUT: Returns 200 Ok + updated DTO
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID id,
                                    @RequestBody ProductDTO product) {
        ProductDTO updatedProduct = productService.updateProductByExternalId(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    // DELETE: Returns 204 if success, 404 if not found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {

        try {
            productService.deleteProductByExternalId(id);
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