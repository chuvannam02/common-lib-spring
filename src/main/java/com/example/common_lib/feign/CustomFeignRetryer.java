package com.example.common_lib.feign;

import feign.RetryableException;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Project: common-lib
 * @Package: com.example.common_lib.feign  *
 * @Author: ChuVanNam
 * @Date: 7/26/2025
 * @Time: 5:05 PM
 */

@Slf4j
public class CustomFeignRetryer implements Retryer {

    private final int maxAttempts;
    private final long period;
    private final long maxPeriod;
    int attempt = 1;

    public CustomFeignRetryer() {
        this(1000, 3000, 3); // delay 1s, max delay 3s, max 3 attempts
    }

    public CustomFeignRetryer(long period, long maxPeriod, int maxAttempts) {
        this.period = period;
        this.maxPeriod = maxPeriod;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        log.warn("Retry attempt {} for request: {}", attempt, e.request());
        if (attempt++ >= maxAttempts) {
            log.error("Max retry attempts reached for request: {}", e.request());
            throw e;
        }
        try {
            // Calculate the next delay, ensuring it does not exceed maxPeriod
            long nextDelay = Math.min(period * attempt, maxPeriod);
            log.info("Retrying in {} milliseconds...", nextDelay);
            TimeUnit.MILLISECONDS.sleep(period);
        } catch (InterruptedException ignored) {
            log.error("Thread interrupted during retry delay", ignored);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Retryer clone() {
        return new CustomFeignRetryer(period, maxPeriod, maxAttempts);
    }
}
