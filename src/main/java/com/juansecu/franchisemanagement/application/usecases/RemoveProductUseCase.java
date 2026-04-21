package com.juansecu.franchisemanagement.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import com.juansecu.franchisemanagement.application.exceptions.HttpException;
import com.juansecu.franchisemanagement.domain.repositories.IProductRepository;

@Service
@RequiredArgsConstructor
public class RemoveProductUseCase {
    private final IProductRepository productRepository;

    public Mono<Void> execute(Long productId) {
        return productRepository
            .findById(productId)
            .switchIfEmpty(
                Mono.error(
                    HttpException.notFound("Product not found with ID: " + productId)
                )
            )
            .flatMap(product -> productRepository.deleteById(productId));
    }
}
