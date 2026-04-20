package com.juansecu.franchisemanagement.domain.repositories;

import com.juansecu.franchisemanagement.domain.models.Branch;
import reactor.core.publisher.Mono;

public interface IBranchRepository {
    Mono<Branch> findByNameAndFranchiseId(String name, Long franchiseId);
    Mono<Branch> save(Branch branch);
}
