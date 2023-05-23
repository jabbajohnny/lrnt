package com.example.lrnt.database;

import com.example.lrnt.config.JwtUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "assets")
public class DatabaseAsset {

    public DatabaseAsset(String title, String description, String userId,
                         UserRepository userRepository,
                         AssetRepository assetRepository) {
        this.title = title;
        this.description = description;
        this.userId = userId;

        // generate asset id -> check whether id is unique -> set id
        String id = RandomStringUtils.randomAlphanumeric(11);
        while (!assetRepository.getDatabaseAssetById(id).isEmpty()) {
            id = RandomStringUtils.randomAlphanumeric(11);
        }
        this.id = id;

        uploadDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void saveFile(MultipartFile file) throws IOException {
        path = "src/main/storage/" + file.getOriginalFilename();
        int i = 1;
        while (Files.exists(Path.of(path))) {
            path = "src/main/storage/" + i++ + file.getOriginalFilename();
        }
        file.transferTo(Files.createFile(Path.of(path)));
    }

    @Id
    @Column(name = "id")
    String id;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "path")
    String path;

    @Column(name = "upload_date")
    String uploadDate;

    @Column(name = "user_id")
    String userId;

    public DatabaseAsset() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
