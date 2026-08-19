/**
 * File: ProductService.java
 * Description: Contains business logic related to products
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.inventorymanager.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.derick01.inventorymanager.repository.ProductRepository;
import com.github.derick01.inventorymanager.dtos.ProductDTO;
import com.github.derick01.inventorymanager.entity.Product;
import com.github.derick01.inventorymanager.mapper.ProductMapper;
import com.github.derick01.inventorymanager.exception.ResourceNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productsAsDtoList = productMapper.toDtoList(products);
        return productsAsDtoList;
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductByExternalId(UUID externalId) {
        Product product = productRepository.findByExternalId(externalId)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                            "Product not found"));
        ProductDTO productAsDTO  = productMapper.toDto(product);
        return productAsDTO;
    }
    
    @Transactional
    public ProductDTO create(ProductDTO productDto) {
        Product product = productMapper.toEntity(productDto);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Transactional
    public ProductDTO updateProductByExternalId(UUID externalId,
                                                ProductDTO updatedProductDto) {
        Product existingProduct = productRepository
                                    .findByExternalId(externalId)
                                    .orElseThrow(() 
                                            -> new ResourceNotFoundException(
                                                "Product not found"));

        existingProduct = productMapper.updateProductFromDto(updatedProductDto,
                                                             existingProduct);

        existingProduct = productRepository.save(existingProduct);

        return productMapper.toDto(existingProduct);
    }

    @Transactional
    public void deleteProductByExternalId(UUID externalId) {
        Product product = productRepository.findByExternalId(externalId)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                            "Product not found"));
        productRepository.delete(product);
    }
}