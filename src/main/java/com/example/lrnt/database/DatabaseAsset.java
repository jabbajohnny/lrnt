package com.example.lrnt.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "assets")
public class DatabaseAsset {

    public DatabaseAsset(String title, String description, UserRepository userRepository) {
        this.title = title;
        this.description = description;
        userId = userRepository.findAllByEmail(SecurityContextHolder.
                        getContext().
                        getAuthentication().
                        getName())
                .get(0).getId();

        // generate asset id -> check whether id is unique -> set id

        uploadDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Id
    @Column(name = "id")
    String id;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "upload_date")
    String uploadDate;

    @Column(name = "user_id")
    String userId;

    public DatabaseAsset() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
