package com.juansecu.franchisemanagement.domain.repositories;

import reactor.core.publisher.Mono;

import com.juansecu.franchisemanagement.domain.models.Franchise;

public interface IFranchiseRepository {
    Mono<Franchise> findById(Long id);
    Mono<Franchise> findByNameIgnoreCase(String name);
    Mono<Franchise> save(Franchise franchise);
}
