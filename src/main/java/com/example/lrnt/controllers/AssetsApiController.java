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
    public ResponseEntity<JsonNode> upload(@RequestPart MultipartFile file) {
        System.out.println(file.getName());
        return null;
    }
}
