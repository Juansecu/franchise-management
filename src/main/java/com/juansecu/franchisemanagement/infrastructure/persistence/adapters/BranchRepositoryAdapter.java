package com.juansecu.franchisemanagement.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import com.juansecu.franchisemanagement.domain.models.Branch;
import com.juansecu.franchisemanagement.domain.repositories.IBranchRepository;
import com.juansecu.franchisemanagement.infrastructure.persistence.entities.BranchEntity;
import com.juansecu.franchisemanagement.infrastructure.persistence.entities.FranchiseEntity;
import com.juansecu.franchisemanagement.infrastructure.persistence.repositories.JpaBranchRepository;

@Component
@RequiredArgsConstructor
public class BranchRepositoryAdapter implements IBranchRepository {
    private final JpaBranchRepository jpaBranchRepository;

    @Override
    public Mono<Branch> findById(final Long branchId) {
        return Mono.fromCallable(() -> jpaBranchRepository.findById(branchId))
            .subscribeOn(Schedulers.boundedElastic())
            .flatMap(optional ->
                optional.map(entity ->
                    Mono.just(
                        Branch.builder()
                            .branchId(entity.getBranchId())
                            .name(entity.getName())
                            .franchiseId(entity.getFranchise().getFranchiseId())
                            .build()
                    )
                ).orElse(Mono.empty())
            );
    }

    @Override
    public Mono<Branch> findByNameAndFranchiseId(
        final String name,
        final Long franchiseId
    ) {
        return Mono.fromCallable(
            () -> jpaBranchRepository.findByNameAndFranchise_FranchiseId(
                name,
                franchiseId
            )
        )
            .subscribeOn(Schedulers.boundedElastic())
            .flatMap(optional ->
                optional.map(entity ->
                    Mono.just(
                        Branch.builder()
                            .branchId(entity.getBranchId())
                            .name(entity.getName())
                            .franchiseId(entity.getFranchise().getFranchiseId())
                            .build()
                    )
                ).orElse(Mono.empty())
            );
    }

    @Override
    public Mono<Branch> save(final Branch branch) {
        return Mono.fromCallable(() -> {
            final BranchEntity entity = BranchEntity.builder()
                    .branchId(branch.getBranchId())
                    .name(branch.getName())
                    .franchise(FranchiseEntity.builder().franchiseId(branch.getFranchiseId()).build())
                    .build();
            final BranchEntity savedEntity = jpaBranchRepository.save(entity);

            return Branch.builder()
                    .branchId(savedEntity.getBranchId())
                    .name(savedEntity.getName())
                    .franchiseId(savedEntity.getFranchise().getFranchiseId())
                    .build();
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
