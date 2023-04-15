package com.example.lrnt;

import com.example.lrnt.config.RsaKeys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication()
@EnableConfigurationProperties(RsaKeys.class)
public class LrntApplication {

    public static void main(String[] args) {
        SpringApplication.run(LrntApplication.class, args);
    }

}
