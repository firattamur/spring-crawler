package com.firattamur.spring_crawler.config;

import com.firattamur.spring_crawler.repository.ProductRepository;
import com.firattamur.spring_crawler.service.JobQueueService;
import com.firattamur.spring_crawler.service.WebScraperService;
import com.firattamur.spring_crawler.worker.CrawlerWorker;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@Slf4j
public class CrawlerWorkerConfig {

    @Value("${crawler.worker.count:5}")
    private int workerCount;

    private final JobQueueService jobQueueService;
    private final ProductRepository productRepository;
    private final WebScraperService webScraperService;

    @Autowired
    public CrawlerWorkerConfig(JobQueueService jobQueueService,
                               ProductRepository productRepository,
                               WebScraperService webScraperService) {
        this.jobQueueService = jobQueueService;
        this.productRepository = productRepository;
        this.webScraperService = webScraperService;
    }

    @Bean
    public List<CrawlerWorker> crawlerWorkers() {
        return IntStream.range(0, workerCount)
                .mapToObj(i -> new CrawlerWorker(i, jobQueueService, productRepository, webScraperService))
                .collect(Collectors.toList());
    }

    @Bean
    public ExecutorService workerExecutorService(List<CrawlerWorker> crawlerWorkers) {
        log.info("Creating worker executor service with {} workers", workerCount);

        ExecutorService executorService = Executors.newFixedThreadPool(workerCount);
        crawlerWorkers.forEach(worker -> {
            executorService.submit(worker);
            log.info("Submitted worker {} to thread pool", worker.getWorkerId());
        });

        return executorService;
    }
}
