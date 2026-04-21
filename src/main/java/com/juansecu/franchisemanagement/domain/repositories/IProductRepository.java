package com.juansecu.franchisemanagement.domain.repositories;

import reactor.core.publisher.Mono;

import com.juansecu.franchisemanagement.domain.models.Product;

public interface IProductRepository {
    Mono<Void> deleteById(Long productId);
    Mono<Product> findById(Long productId);
    Mono<Product> findByNameAndBranchId(String name, Long branchId);
    Mono<Product> save(Product product);
}
