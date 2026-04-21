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
class UpdateProductNameUseCaseTest {
    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private UpdateProductNameUseCase updateProductNameUseCase;

    @Test
    void execute_shouldUpdateProductNameWhenValid() {
        Long productId = 1L;
        Long branchId = 10L;
        String newName = "New Product Name";
        Product existingProduct = Product.builder()
            .productId(productId)
            .branchId(branchId)
            .name("Old Name")
            .build();
        Product updatedProduct = Product.builder()
            .productId(productId)
            .branchId(branchId)
            .name(newName)
            .build();

        when(
            productRepository.findById(productId)
        ).thenReturn(Mono.just(existingProduct));
        when(
            productRepository.findByNameAndBranchId(newName, branchId)
        ).thenReturn(Mono.empty());
        when(
            productRepository.save(any(Product.class))
        ).thenReturn(Mono.just(updatedProduct));

        updateProductNameUseCase
            .execute(productId, newName)
            .as(StepVerifier::create)
            .expectNextMatches(p -> p.getName().equals(newName))
            .verifyComplete();

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void execute_shouldReturnErrorWhenNameExistsInSameBranch() {
        Long productId = 1L;
        Long branchId = 10L;
        String newName = "Existing Name";
        Product existingProduct = Product.builder()
            .productId(productId)
            .branchId(branchId)
            .name("Old Name")
            .build();
        Product otherProduct = Product.builder()
            .productId(2L)
            .branchId(branchId)
            .name(newName)
            .build();

        when(
            productRepository.findById(productId)
        ).thenReturn(Mono.just(existingProduct));
        when(
            productRepository.findByNameAndBranchId(newName, branchId)
        ).thenReturn(Mono.just(otherProduct));

        updateProductNameUseCase
            .execute(productId, newName)
            .as(StepVerifier::create)
            .expectErrorMessage("Product with name " + newName + " already exists in this branch")
            .verify();

        verify(productRepository, never()).save(any(Product.class));
    }
}
