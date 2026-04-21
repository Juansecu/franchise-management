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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveProductUseCaseTest {
    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private RemoveProductUseCase removeProductUseCase;

    @Test
    void execute_shouldDeleteProductWhenFound() {
        Long productId = 1L;
        Product product = Product.builder().productId(productId).name("Test Product").stock(10).branchId(1L).build();

        when(productRepository.findById(productId)).thenReturn(Mono.just(product));
        when(productRepository.deleteById(productId)).thenReturn(Mono.empty());

        removeProductUseCase.execute(productId)
                .as(StepVerifier::create)
                .verifyComplete();

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void execute_shouldReturnErrorWhenProductNotFound() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Mono.empty());

        removeProductUseCase.execute(productId)
                .as(StepVerifier::create)
                .expectErrorMessage("Product not found with ID: " + productId)
                .verify();

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).deleteById(anyLong());
    }
}
