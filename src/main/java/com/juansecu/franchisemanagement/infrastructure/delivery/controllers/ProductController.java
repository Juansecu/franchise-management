package com.juansecu.franchisemanagement.infrastructure.delivery.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import com.juansecu.franchisemanagement.application.usecases.AddProductUseCase;
import com.juansecu.franchisemanagement.application.usecases.RemoveProductUseCase;
import com.juansecu.franchisemanagement.application.usecases.UpdateProductStockUseCase;
import com.juansecu.franchisemanagement.infrastructure.delivery.dtos.requests.AddProductRequest;
import com.juansecu.franchisemanagement.infrastructure.delivery.dtos.requests.UpdateStockRequest;
import com.juansecu.franchisemanagement.infrastructure.delivery.dtos.responses.ProductResponse;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final AddProductUseCase addProductUseCase;
    private final RemoveProductUseCase removeProductUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponse> addProduct(
        @Valid @RequestBody AddProductRequest request
    ) {
        return addProductUseCase
            .execute(
                request.getName(),
                request.getStock(),
                request.getBranchId()
            )
            .map(product ->
                ProductResponse.builder()
                    .productId(product.getProductId())
                    .name(product.getName())
                    .stock(product.getStock())
                    .branchId(product.getBranchId())
                    .build()
            );
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> removeProduct(@PathVariable Long productId) {
        return removeProductUseCase.execute(productId);
    }

    @PatchMapping("/{productId}/stock")
    public Mono<ProductResponse> updateStock(
        @PathVariable Long productId,
        @Valid @RequestBody UpdateStockRequest request
    ) {
        return updateProductStockUseCase
            .execute(productId, request.getStock())
            .map(product ->
                ProductResponse.builder()
                    .productId(product.getProductId())
                    .name(product.getName())
                    .stock(product.getStock())
                    .branchId(product.getBranchId())
                    .build()
            );
    }
}
