package com.firattamur.spring_crawler.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParsedProductData {

    private String title;
    private Double price;
    private String description;
    private String merchantName;
    private LocalDateTime crawledAt;

}
