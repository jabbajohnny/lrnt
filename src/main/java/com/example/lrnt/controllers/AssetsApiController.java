package com.example.lrnt.controllers;

import com.example.lrnt.assets.Asset;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AssetsApiController {

    @PostMapping("/api/upload")
    public ResponseEntity<JsonNode> upload(@RequestPart("file") MultipartFile file,
                                           @RequestPart("title") String title,
                                           @RequestPart("description") String description) throws JsonProcessingException {
        //if file is not mp3 or any other audio format, return error
        ObjectMapper mapper = new ObjectMapper();

        if (file != null && !file.isEmpty() && !file.getContentType().equals("audio/mpeg")) {
            JsonNode json = mapper.readTree(String.format("{\"error\": \"%s\"}", "Not an audio format!"));
            return ResponseEntity.ok(json);
        }

        //if file is ok, save the file, create an asset, store info about asset in database table
        Asset asset = new Asset(title, description, file);
        return null;
    }
}
