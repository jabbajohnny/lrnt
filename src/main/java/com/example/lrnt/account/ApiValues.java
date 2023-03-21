package com.example.lrnt.account;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class ApiValues {
    public String apiKey;
    public String url;

    public ApiValues() {
        try (FileInputStream file = new FileInputStream("src/main/resources/application.properties")) {
            Properties properties = new Properties();
            properties.load(new InputStreamReader(file));

            url = properties.getProperty("api.values.url");
            apiKey = properties.getProperty("api.values.apiKey");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
