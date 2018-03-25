package com.priceseries.datagenerator.tasks;

import com.priceseries.datagenerator.service.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;

@Component
public class PriceGeneratorTask {

    private static final Logger log = LoggerFactory.getLogger(PriceGeneratorTask.class);
    private RestService restService;

    public PriceGeneratorTask(RestService restService) {
        this.restService = restService;
    }

    public static BigDecimal generateNumber() {
        Random r = new Random();
        int multiplier = 10000;
        if (r.nextInt(100000) % 2 == 0) {
            multiplier = 1000;
        }
        return new BigDecimal((Math.random() * multiplier) + Math.random());
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void generateAndPostPriceData() {
        restService.postData(generateNumber());
    }
}