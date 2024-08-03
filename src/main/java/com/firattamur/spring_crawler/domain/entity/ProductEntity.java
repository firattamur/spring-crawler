package com.firattamur.spring_crawler.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "products",
        indexes = {
        @Index(name = "idx_url", columnList = "url", unique = true)
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_url", columnNames = {"url"})
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "crawled_at")
    private LocalDateTime crawledAt;

    private Double price;
    private String title;

    @Column(length = 3000)
    private String description;
    private String merchantName;

    @Enumerated(EnumType.STRING)
    private CrawlingStatus crawlingStatus;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.crawlingStatus = CrawlingStatus.PENDING;
    }

}

