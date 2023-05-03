package com.example.lrnt.assets;

import org.springframework.web.multipart.MultipartFile;

public record Asset(String title, String description, MultipartFile file) {
}
