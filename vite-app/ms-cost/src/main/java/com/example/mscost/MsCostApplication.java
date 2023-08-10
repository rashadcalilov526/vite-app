package com.example.mscost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsCostApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCostApplication.class, args);
    }

}