package com.example.lrnt.controllers;

import com.example.lrnt.assets.Asset;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AssetsApiController {

    @PostMapping("/api/upload")
    public ResponseEntity<JsonNode> upload(@RequestPart("file") MultipartFile file, @RequestPart("title") String title,
                                           @RequestPart("description") String description) {
        System.out.println(file.getName());
        System.out.println(title);
        System.out.println(description);
        return null;
    }
}
