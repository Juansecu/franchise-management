package com.juansecu.franchisemanagement.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Builder
@Entity(name = "products")
@Getter
@NoArgsConstructor
@Setter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer stock;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private BranchEntity branch;
}
