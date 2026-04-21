package com.juansecu.franchisemanagement.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import com.juansecu.franchisemanagement.application.exceptions.HttpException;
import com.juansecu.franchisemanagement.domain.models.Product;
import com.juansecu.franchisemanagement.domain.repositories.IBranchRepository;
import com.juansecu.franchisemanagement.domain.repositories.IProductRepository;

@Service
@RequiredArgsConstructor
public class AddProductUseCase {
    private final IBranchRepository branchRepository;
    private final IProductRepository productRepository;

    public Mono<Product> execute(
        final String name,
        final Integer stock,
        final Long branchId
    ) {
        return branchRepository
            .findById(branchId)
            .switchIfEmpty(
                Mono.error(
                    HttpException.notFound("Branch not found with ID: " + branchId)
                )
            )
            .flatMap(
                branch -> productRepository.findByNameAndBranchId(name, branchId)
            )
            .flatMap(existingProduct ->
                Mono.<Product>error(
                    HttpException.conflict("Product with name " + name + " already exists in this branch")
                )
            )
            .switchIfEmpty(
                Mono.defer(() -> {
                    final Product product = Product.builder()
                        .name(name)
                        .stock(stock)
                        .branchId(branchId)
                        .build();

                    return productRepository.save(product);
                })
            );
    }
}
