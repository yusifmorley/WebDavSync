package com.yusif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WebDavSyncApplication {
    public static void main(String[] args) {

        SpringApplication.run(WebDavSyncApplication.class, args);

    }
}
