package com.juansecu.franchisemanagement.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import com.juansecu.franchisemanagement.application.exceptions.HttpException;
import com.juansecu.franchisemanagement.domain.models.Franchise;
import com.juansecu.franchisemanagement.domain.repositories.IFranchiseRepository;

@RequiredArgsConstructor
@Service
public class CreateFranchiseUseCase {
    private final IFranchiseRepository franchiseRepository;

    public Mono<Franchise> execute(final String name) {
        return franchiseRepository
            .findByNameIgnoreCase(name)
            .flatMap(existingFranchise ->
                Mono.<Franchise>error(
                    HttpException.conflict(
                        "Franchise with name " + name + " already exists"
                    )
                )
            )
            .switchIfEmpty(Mono.defer(() -> {
                final Franchise franchise = Franchise.builder()
                    .name(name)
                    .build();

                return franchiseRepository.save(franchise);
            }));
    }
}
