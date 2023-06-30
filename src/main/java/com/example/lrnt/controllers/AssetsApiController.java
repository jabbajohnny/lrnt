package com.example.lrnt.controllers;

import com.example.lrnt.audio.AudioUtils;
import com.example.lrnt.config.JwtUtils;
import com.example.lrnt.database.AssetRepository;
import com.example.lrnt.database.DatabaseAsset;
import com.example.lrnt.database.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        if (file != null && !file.isEmpty() && !file.getContentType().equals("audio/wav")) {
            JsonNode json = mapper.readTree(String.format("{\"error\": \"%s\"}", "Not a supported audio format! Please use wav format."));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
        }

        //if file is ok, save the file, create an asset, store info about asset in database table
        DatabaseAsset asset = new DatabaseAsset(title, description, jwtUtils.getId(token), assetRepository);
        asset.saveFile(file);
        assetRepository.save(asset);

        JsonNode json = mapper.readTree(String.format("{\"ok\": \"%s\"}", "done"));
        return ResponseEntity.ok(json);
    }

    @GetMapping("/api/asset/{assetId}")
    public ResponseEntity<JsonNode> getAssetData(@PathVariable("assetId") String id) {
        List<DatabaseAsset> assets;

        if (id.equals("all")) {
            assets = assetRepository.getAll();
        } else {
            assets = assetRepository.getDatabaseAssetById(id);
        }

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

    @GetMapping(value = "/api/asset/{assetId}/audio", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> getAudio(@PathVariable("assetId") String assetId,
                                                        @RequestParam(name = "seek", required = false) String seekPosition) throws IOException,
                                                                                                                                   UnsupportedAudioFileException {
        Path path  = Paths.get(assetRepository.getDatabaseAssetById(assetId).get(0).getPath());
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(path.toFile());


        File temp = File.createTempFile("temp", ".wav");

        if(seekPosition != null) {
            double seek = Double.parseDouble(seekPosition);

            AudioFormat format = audioInputStream.getFormat();
            long endFrame = audioInputStream.getFrameLength();
            long startFrame = (long)(seek * format.getFrameRate());

            audioInputStream.skip(startFrame * format.getFrameSize());

            audioInputStream = new AudioInputStream(audioInputStream, format, endFrame - startFrame);

            System.out.println("new position: " + seek);
        }

        InputStreamResource resource;

        if (path.toAbsolutePath().toString().endsWith(".wav")) {
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, temp);
            resource = new InputStreamResource(new FileInputStream(temp));
        } else {
            resource = new InputStreamResource(audioInputStream);
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
