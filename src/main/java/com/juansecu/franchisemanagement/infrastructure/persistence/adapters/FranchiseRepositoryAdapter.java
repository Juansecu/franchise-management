package com.juansecu.franchisemanagement.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import com.juansecu.franchisemanagement.domain.models.Franchise;
import com.juansecu.franchisemanagement.domain.repositories.IFranchiseRepository;
import com.juansecu.franchisemanagement.infrastructure.persistence.entities.FranchiseEntity;
import com.juansecu.franchisemanagement.infrastructure.persistence.repositories.JpaFranchiseRepository;

@Component
@RequiredArgsConstructor
public class FranchiseRepositoryAdapter implements IFranchiseRepository {
    private final JpaFranchiseRepository jpaFranchiseRepository;

    @Override
    public Mono<Franchise> findById(final Long id) {
        return Mono.fromCallable(() ->jpaFranchiseRepository.findById(id))
            .subscribeOn(Schedulers.boundedElastic())
            .flatMap(optional ->
                optional
                    .map(entity -> Mono.just(
                        Franchise.builder()
                            .franchiseId(entity.getFranchiseId())
                            .name(entity.getName())
                            .build()
                    ))
                    .orElse(Mono.empty())
            );
    }

    @Override
    public Mono<Franchise> findByNameIgnoreCase(final String name) {
        return Mono.fromCallable(
            () -> jpaFranchiseRepository
                .findByNameIgnoreCase(name))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optional ->
                    optional
                        .map(entity ->
                            Mono.just(Franchise.builder()
                                .franchiseId(entity.getFranchiseId())
                                .name(entity.getName())
                                .build()
                            )
                        )
                        .orElse(Mono.empty()));
    }

    @Override
    public Mono<Franchise> save(final Franchise franchise) {
        return Mono.fromCallable(() -> {
            final FranchiseEntity entity = FranchiseEntity.builder()
                .franchiseId(franchise.getFranchiseId())
                .name(franchise.getName())
                .build();
            final FranchiseEntity savedEntity = jpaFranchiseRepository.save(entity);

            return Franchise.builder()
                .franchiseId(savedEntity.getFranchiseId())
                .name(savedEntity.getName())
                .build();
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
