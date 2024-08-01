package com.firattamur.spring_crawler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JobQueueService {

    @Value("${crawler.job.queue.name}")
    private String CRAWLER_JOB_QUEUE;

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public JobQueueService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void enqueueJob(String url) {
        redisTemplate.opsForList().rightPush(CRAWLER_JOB_QUEUE, url);
    }

    public String dequeueJob(long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().leftPop(CRAWLER_JOB_QUEUE, timeout, unit);
    }

}
