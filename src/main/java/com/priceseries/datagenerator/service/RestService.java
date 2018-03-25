package com.priceseries.datagenerator.service;

import com.google.gson.Gson;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;

@Service
@PropertySource("classpath:/application.properties")
public class RestService {

    private static final Logger log = LoggerFactory.getLogger(RestService.class);
    @Value("${price.app.url}")
    private String priceAppUrl;

    @Value("${price.param.name}")
    private String propertyName;

    public void postData(BigDecimal price) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(priceAppUrl);
        String jsonString = new Gson().toJson(Collections.singletonMap(propertyName, price));

        try {
            log.info("sending post request");
            post.setEntity(new StringEntity(jsonString, "UTF-8"));
            post.setHeader("Content-type", "application/json");
            client.execute(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}