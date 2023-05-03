package com.example.lrnt.controllers;

import com.example.lrnt.assets.Asset;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AssetsApiController {

    @PostMapping("/api/upload")
    public ResponseEntity<JsonNode> upload(@RequestBody Asset asset) {
        System.out.println(asset.description());
        return null;
    }
}
