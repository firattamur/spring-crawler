package com.firattamur.spring_crawler.domain.dto;

import lombok.Data;

@Data
public class ProductOutDto {

    private Long id;
    private String url;
    private CrawlingStatus status;

    enum CrawlingStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        FAILED
    }

}
