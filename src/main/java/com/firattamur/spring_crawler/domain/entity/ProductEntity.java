package com.firattamur.spring_crawler.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String url;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "crawled_at")
    private LocalDateTime crawledAt;

    private Double price;
    private String title;
    private String description;
    private String merchantName;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.productStatus = ProductStatus.PENDING;
    }

}

