package com.sample.apiclient.configurations;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    // mengatur bean configuration untuk object restTemplate
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.

        // mengatur koneksi time out (jika koneksi lebih dari 5 detik baka koneksi akan
        // terputus)
                setConnectTimeout(Duration.ofSeconds(5)).
                // mengatur koneksi time out (jika koneksi lebih dari 5 detik baka koneksi akan
                // terputus)
                setReadTimeout(Duration.ofSeconds(5))
                .build();
    }
}
