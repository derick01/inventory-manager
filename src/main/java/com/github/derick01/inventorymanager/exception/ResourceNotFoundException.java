/**
 * File: ResourceNotFoundException.java
 * Description: Implementation for ResourceNotFoundException
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.pantry_tracker.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}