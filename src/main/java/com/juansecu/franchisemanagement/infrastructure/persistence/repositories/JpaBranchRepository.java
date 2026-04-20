package com.juansecu.franchisemanagement.infrastructure.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juansecu.franchisemanagement.infrastructure.persistence.entities.BranchEntity;

@Repository
public interface JpaBranchRepository extends JpaRepository<BranchEntity, Long> {
    Optional<BranchEntity> findByNameAndFranchise_FranchiseId(String name, Long franchiseId);
}
