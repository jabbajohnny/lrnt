package com.example.lrnt.assets;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public record Asset(String title, String description, MultipartFile file) {

    public void save() {

    }
}

