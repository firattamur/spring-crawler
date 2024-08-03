package com.firattamur.spring_crawler.domain.dto;

import com.firattamur.spring_crawler.domain.entity.ProductStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductResponseDto {

    private Long id;
    private String url;
    private Long price;
    private String title;
    private String description;
    private String merchantName;
    private ProductStatus productStatus;
    private LocalDateTime createdAt;
    private LocalDateTime crawledAt;

}
