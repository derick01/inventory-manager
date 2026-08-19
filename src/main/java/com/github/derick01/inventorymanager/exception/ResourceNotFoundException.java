/**
 * File: ResourceNotFoundException.java
 * Description: Implementation for ResourceNotFoundException
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.inventorymanager.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}