/**
 * File: BatchRepository.java
 * Description: Contains batch repository code
 * 
 * Author: Derick Canceran
 */

package com.github.derick01.pantry_tracker.repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import com.github.derick01.pantry_tracker.entity.Batch;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    Optional<Batch> findByExternalId(UUID externalId);

    @Query("SELECT b " +
           "FROM Batch b " +
           "WHERE b.expiryDate >= CURRENT_DATE " +
           "AND b.expiryDate <= :boundaryDate " +
           "ORDER BY b.expiryDate ASC")
    List<Batch> findBatchesExpiringBefore(@Param("boundaryDate") LocalDate boundaryDate);
}