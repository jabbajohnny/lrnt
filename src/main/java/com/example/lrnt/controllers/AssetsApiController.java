package com.example.lrnt.controllers;

import com.example.lrnt.config.JwtUtils;
import com.example.lrnt.database.AssetRepository;
import com.example.lrnt.database.DatabaseAsset;
import com.example.lrnt.database.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class AssetsApiController {

    private final AssetRepository assetRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public AssetsApiController(AssetRepository assetRepository, UserRepository userRepository, JwtUtils jwtUtils) {
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/api/upload")
    public ResponseEntity<JsonNode> upload(@RequestPart("file") MultipartFile file,
                                           @RequestPart("title") String title,
                                           @RequestPart("description") String description,
                                           @CookieValue(name = "token", defaultValue = "") String token
    ) throws IOException {
        //if file is not mp3 or any other audio format, return error
        ObjectMapper mapper = new ObjectMapper();

        if (file != null && !file.isEmpty() && (!file.getContentType().equals("audio/mpeg") &&
                                               !file.getContentType().equals("audio/wav"))) {
            JsonNode json = mapper.readTree(String.format("{\"error\": \"%s\"}", "Not an audio format!"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
        }

        //if file is ok, save the file, create an asset, store info about asset in database table
        DatabaseAsset asset = new DatabaseAsset(title, description, jwtUtils.getId(token), userRepository, assetRepository);
        asset.saveFile(file);
        assetRepository.save(asset);

        JsonNode json = mapper.readTree(String.format("{\"ok\": \"%s\"}", "done"));
        return ResponseEntity.ok(json);
    }
}
