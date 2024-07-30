package com.firattamur.spring_crawler.domain.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String url;
    private LocalDateTime createdAt;
    private LocalDateTime crawledAt;

    private Long price;
    private String title;
    private String description;
    private String qa;
    private String merchantName;

    @Enumerated(EnumType.STRING)
    private ProductStatus crawlingStatus;

}

