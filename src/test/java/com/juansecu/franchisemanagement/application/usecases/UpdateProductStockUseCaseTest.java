package com.juansecu.franchisemanagement.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import com.juansecu.franchisemanagement.domain.models.Product;
import com.juansecu.franchisemanagement.domain.repositories.IProductRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProductStockUseCaseTest {
    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private UpdateProductStockUseCase updateProductStockUseCase;

    @Test
    void execute_shouldUpdateStockWhenProductExists() {
        Long productId = 1L;
        Integer newStock = 20;
        Product product = Product.builder()
            .productId(productId)
            .name("Product")
            .stock(10)
            .branchId(1L)
            .build();
        Product updatedProduct = Product.builder()
            .productId(productId)
            .name("Product")
            .stock(newStock)
            .branchId(1L).build();

        when(
            productRepository.findById(productId)
        ).thenReturn(Mono.just(product));
        when(
            productRepository.save(any(Product.class))
        ).thenReturn(Mono.just(updatedProduct));

        updateProductStockUseCase
            .execute(productId, newStock)
            .as(StepVerifier::create)
            .expectNextMatches(p -> p.getStock().equals(newStock))
            .verifyComplete();

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void execute_withNegativeStock_shouldReturnError() {
        updateProductStockUseCase
            .execute(1L, -5)
            .as(StepVerifier::create)
            .expectErrorMessage("Stock cannot be negative")
            .verify();

        verify(productRepository, never()).findById(anyLong());
    }
}
