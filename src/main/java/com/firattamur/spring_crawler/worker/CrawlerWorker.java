package com.firattamur.spring_crawler.worker;

import com.firattamur.spring_crawler.domain.entity.ProductStatus;
import com.firattamur.spring_crawler.domain.model.ParsedProductData;
import com.firattamur.spring_crawler.service.JobQueueService;
import com.firattamur.spring_crawler.service.ProductService;
import com.firattamur.spring_crawler.service.WebScraperService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
public class CrawlerWorker implements Runnable {

    private final int workerId;
    private final JobQueueService jobQueueService;
    private final ProductService productService;
    private final WebScraperService webScraperService;
    private boolean running = true;

    public CrawlerWorker(int workerId, JobQueueService jobQueueService,
                         ProductService productService,
                         WebScraperService webScraperService) {
        this.workerId = workerId;
        this.jobQueueService = jobQueueService;
        this.productService = productService;
        this.webScraperService = webScraperService;
    }

    @Override
    public void run() {

        while (running) {
            try {

                String url = jobQueueService.dequeueJob(30, TimeUnit.SECONDS);
                if (url != null) {
                    productService.setProductStatus(url, ProductStatus.IN_PROGRESS);
                    processJob(url);
                }

            } catch (Exception e) {
                log.error("Error in worker loop", e);
            }
        }

    }

    private void processJob(String url) {
        log.info("Worker Thread: {}, Scraping Url: {}", Thread.currentThread().getName(), url);

        try {

            Optional<ParsedProductData> product = webScraperService.scrape(url);
            product.ifPresent(parsedProductData -> productService.update(parsedProductData, url));
            productService.setProductStatus(url, ProductStatus.DONE);

        } catch (Exception e) {

            log.error("Error while scraping: {}", url, e);
            productService.setProductStatus(url, ProductStatus.FAILED);

        }

    }

    public void stop() {
        this.running = false;
    }

}
