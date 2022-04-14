package com.aj2814.springLearning.metrics;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class MetricsClient {
    private static final String RATE_LIMIT_REMAIN_URL = "http://localhost:8080/actuator/metrics/github.ratelimit.remaining";
    private final RestTemplate restTemplate;

    public MetricsClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public int fetchRateRemain() {

        byte[] base64CredsBytes = Base64.encodeBase64("admin:admin".getBytes());
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<RateLimitRemain> entity = this.restTemplate.exchange(RATE_LIMIT_REMAIN_URL, HttpMethod.GET, requestEntity, RateLimitRemain.class);
        return Objects.requireNonNull(entity.getBody()).getMeasurements();
    }
}
