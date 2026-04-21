package com.juansecu.franchisemanagement.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import com.juansecu.franchisemanagement.domain.models.Branch;
import com.juansecu.franchisemanagement.domain.models.Product;
import com.juansecu.franchisemanagement.domain.repositories.IBranchRepository;
import com.juansecu.franchisemanagement.domain.repositories.IProductRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddProductUseCaseTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private IBranchRepository branchRepository;

    @InjectMocks
    private AddProductUseCase addProductUseCase;

    @Test
    void execute_shouldSaveAndReturnProduct() {
        String name = "New Product";
        Integer stock = 10;
        Long branchId = 1L;
        Branch branch = Branch.builder()
            .branchId(branchId)
            .name("Branch")
            .build();
        Product product = Product.builder()
            .productId(1L)
            .name(name)
            .stock(stock)
            .branchId(branchId)
            .build();

        when(
            branchRepository.findById(branchId)
        ).thenReturn(Mono.just(branch));
        when(
            productRepository.findByNameAndBranchId(name, branchId)
        ).thenReturn(Mono.empty());
        when(
            productRepository.save(any(Product.class))
        ).thenReturn(Mono.just(product));

        addProductUseCase
            .execute(name, stock, branchId)
            .as(StepVerifier::create)
            .expectNextMatches(p ->
                p.getProductId().equals(1L) &&
                p.getName().equals(name)
            )
            .verifyComplete();
    }

    @Test
    void execute_whenBranchNotFound_shouldReturnError() {
        when(branchRepository.findById(1L)).thenReturn(Mono.empty());

        addProductUseCase
            .execute("Product", 10, 1L)
            .as(StepVerifier::create)
            .expectErrorMessage("Branch not found with ID: 1")
            .verify();
    }
}
