package com.curedfish.vivo.ssoclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SsoClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoClientApplication.class, args);
    }

}
