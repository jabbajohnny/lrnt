package com.example.lrnt.controllers;

import com.example.lrnt.config.JwtUtils;
import com.example.lrnt.database.AssetRepository;
import com.example.lrnt.database.DatabaseAsset;
import com.example.lrnt.database.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

@Controller
public class AssetsApiController {

    private final AssetRepository assetRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final ObjectMapper mapper;


    private final JsonNodeFactory factory = JsonNodeFactory.instance;

    public AssetsApiController(AssetRepository assetRepository, UserRepository userRepository, JwtUtils jwtUtils, ObjectMapper objectMapper) {
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.mapper = objectMapper;
    }

    @PostMapping("/api/upload")
    public ResponseEntity<JsonNode> upload(@RequestPart("file") MultipartFile file,
                                           @RequestPart("title") String title,
                                           @RequestPart("description") String description,
                                           @CookieValue(name = "token", defaultValue = "") String token
    ) throws IOException {
        //if file is not mp3 or any other audio format, return error

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

    @GetMapping("/api/assets")
    public ResponseEntity<JsonNode> getAssets() {
        List<DatabaseAsset> assets = assetRepository.getAll();
        System.out.println(assets);

        ArrayNode node = factory.arrayNode();

        assets.forEach(asset -> {
            ObjectNode objectNode = factory.objectNode();
            objectNode.put("id", asset.getId());
            objectNode.put("title", asset.getTitle());
            objectNode.put("description", asset.getDescription());
            objectNode.put("author", userRepository.findAllById(asset.getUserId()).get(0).getUsername());
            objectNode.put("upload_date", asset.getUploadDate());

            node.add(objectNode);
        });

        return ResponseEntity.ok(node);
    }

    /* TODO:
        getAssetData() and getAssets() are sharing most of the code.
        One method should be made which will serve 2 purposes
                - get 1 asset from id
                - get all assets when no id is provided

     */
    @GetMapping("/api/asset/{assetId}")
    public ResponseEntity<JsonNode> getAssetData(@PathVariable("assetId") String id) {
        DatabaseAsset asset = assetRepository.getDatabaseAssetById(id).get(0);

        ArrayNode node = factory.arrayNode();

        ObjectNode objectNode = factory.objectNode();

        objectNode.put("id", asset.getId());
        objectNode.put("title", asset.getTitle());
        objectNode.put("description", asset.getDescription());
        objectNode.put("author", userRepository.findAllById(asset.getUserId()).get(0).getUsername());
        objectNode.put("upload_date", asset.getUploadDate());

        node.add(objectNode);

        return ResponseEntity.ok(node);
    }
}
