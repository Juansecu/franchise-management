package com.juansecu.franchisemanagement.infrastructure.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juansecu.franchisemanagement.infrastructure.persistence.entities.ProductEntity;

@Repository
public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByNameAndBranch_BranchId(String name, Long branchId);
}
