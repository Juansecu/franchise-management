package com.juansecu.franchisemanagement.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Builder
@Entity(name = "franchises")
@Getter
@NoArgsConstructor
@Setter
public class FranchiseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long franchiseId;
    @Column(length = 100, nullable = false, unique = true)
    private String name;
}
