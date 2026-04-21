package com.juansecu.franchisemanagement.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import com.juansecu.franchisemanagement.domain.models.Product;
import com.juansecu.franchisemanagement.domain.repositories.IProductRepository;
import com.juansecu.franchisemanagement.infrastructure.persistence.entities.BranchEntity;
import com.juansecu.franchisemanagement.infrastructure.persistence.entities.ProductEntity;
import com.juansecu.franchisemanagement.infrastructure.persistence.repositories.JpaProductRepository;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements IProductRepository {
    private final JpaProductRepository jpaProductRepository;

    @Override
    public Mono<Product> findByNameAndBranchId(
        final String name,
        final Long branchId
    ) {
        return Mono.fromCallable(
                () -> jpaProductRepository.findByNameAndBranch_BranchId(name, branchId)
            )
            .subscribeOn(Schedulers.boundedElastic())
            .flatMap(optional ->
                optional.map(entity ->
                        Mono.just(
                            Product.builder()
                                .productId(entity.getProductId())
                                .name(entity.getName())
                                .stock(entity.getStock())
                                .branchId(entity.getBranch().getBranchId())
                                .build()
                        ))
                    .orElse(Mono.empty())
            );
    }

    @Override
    public Mono<Product> save(Product product) {
        return Mono.fromCallable(() -> {
            ProductEntity entity = ProductEntity.builder()
                    .name(product.getName())
                    .stock(product.getStock())
                    .branch(BranchEntity.builder().branchId(product.getBranchId()).build())
                    .build();
            ProductEntity savedEntity = jpaProductRepository.save(entity);
            return Product.builder()
                    .productId(savedEntity.getProductId())
                    .name(savedEntity.getName())
                    .stock(savedEntity.getStock())
                    .branchId(savedEntity.getBranch().getBranchId())
                    .build();
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
