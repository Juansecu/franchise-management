package com.juansecu.franchisemanagement.domain.repositories;

import reactor.core.publisher.Mono;

import com.juansecu.franchisemanagement.domain.models.Branch;

public interface IBranchRepository {
    Mono<Branch> findById(Long branchId);
    Mono<Branch> findByNameAndFranchiseId(String name, Long franchiseId);
    Mono<Branch> save(Branch branch);
}
