package com.speakfluid.backend.controller;

import com.speakfluid.backend.model.LoadTranscript;
import com.speakfluid.backend.service.TranscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("transcript")
public class TranscriptController {

    @Autowired
    private TranscriptService transcriptService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("transcript")MultipartFile transcript) throws IOException {
        return new ResponseEntity<>(transcriptService.addTranscript(transcript), HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        LoadTranscript loadTranscript = transcriptService.downloadTranscript(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(loadTranscript.getTranscriptType() ))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; transcriptname=\"" + loadTranscript.getTranscriptname() + "\"")
                .body(new ByteArrayResource(loadTranscript.getTranscript()));
    }

}