package com.juansecu.franchisemanagement.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import com.juansecu.franchisemanagement.application.exceptions.HttpException;
import com.juansecu.franchisemanagement.domain.models.Product;
import com.juansecu.franchisemanagement.domain.repositories.IProductRepository;

@Service
@RequiredArgsConstructor
public class UpdateProductStockUseCase {
    private final IProductRepository productRepository;

    public Mono<Product> execute(Long productId, Integer newStock) {
        if (newStock < 0)
            return Mono.error(
                HttpException.badRequest("Stock cannot be negative")
            );

        return productRepository
            .findById(productId)
            .switchIfEmpty(
                Mono.error(
                    HttpException.notFound("Product not found with ID: " + productId)
                )
            )
            .flatMap(product -> {
                product.setStock(newStock);
                return productRepository.save(product);
            });
    }
}
