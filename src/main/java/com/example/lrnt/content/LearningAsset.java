package com.example.lrnt.content;

import com.example.lrnt.MyFirstApp;
import com.example.lrnt.database.AppJdbcTemplate;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LearningAsset {
    String title;
    String description;
    String author;
    String category;
    String id;

    public LearningAsset(String title, String description, String author, String category) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.category = category;
        this.id = RandomStringUtils.randomAlphanumeric(11);

        String addAssetQuery = "INSERT INTO [lrnt].[dbo].[learning_assets]" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        MyFirstApp.jdbcTemplate.update(addAssetQuery,
                id,
                title,
                description,
                author,
                category,
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
}
