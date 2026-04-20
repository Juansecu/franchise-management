package com.juansecu.franchisemanagement.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Builder
@Entity(name = "branches")
@Getter
@NoArgsConstructor
@Setter
public class BranchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;
    @Column(nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "franchise_id", nullable = false)
    private FranchiseEntity franchise;
}
