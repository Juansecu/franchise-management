package com.juansecu.franchisemanagement.infrastructure.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juansecu.franchisemanagement.infrastructure.persistence.entities.FranchiseEntity;

@Repository
public interface JpaFranchiseRepository extends JpaRepository<FranchiseEntity, Long> {
    Optional<FranchiseEntity> findByNameIgnoreCase(String name);
}
