/**
 * File: BatchRepository.java
 * Description: Contains batch repository code
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.pantry_tracker.repository;

import java.util.UUID;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.derick01.pantry_tracker.entity.Batch;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    Optional<Batch> findByExternalId(UUID externalId);
}