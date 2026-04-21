package com.juansecu.franchisemanagement.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import com.juansecu.franchisemanagement.application.exceptions.HttpException;
import com.juansecu.franchisemanagement.domain.models.Product;
import com.juansecu.franchisemanagement.domain.repositories.IProductRepository;

@Service
@RequiredArgsConstructor
public class UpdateProductNameUseCase {
    private final IProductRepository productRepository;

    public Mono<Product> execute(final Long productId, final String newName) {
        return productRepository
            .findById(productId)
            .switchIfEmpty(
                Mono.error(
                    HttpException.notFound("Product not found with ID: " + productId)
                )
            )
            .flatMap(existingProduct ->
                productRepository
                    .findByNameAndBranchId(newName, existingProduct.getBranchId())
                    .flatMap(productWithSameName -> {
                        if (!productWithSameName.getProductId().equals(productId))
                            return Mono.error(
                                HttpException.conflict("Product with name " + newName + " already exists in this branch")
                            );

                        return Mono.just(existingProduct);
                    })
                    .switchIfEmpty(Mono.just(existingProduct))
                    .flatMap(productToUpdate -> {
                        productToUpdate.setName(newName);
                        return productRepository.save(productToUpdate);
                    })
            );
    }
}
