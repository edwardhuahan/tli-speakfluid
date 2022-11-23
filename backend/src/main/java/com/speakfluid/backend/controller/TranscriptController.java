package com.speakfluid.backend.controller;

import com.speakfluid.backend.entities.Dialogue;
import com.speakfluid.backend.entities.WozMessage;
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
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@CrossOrigin("*")
@RequestMapping("transcript")
public class TranscriptController {

    @Autowired
    private TranscriptService transcriptService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("transcript")MultipartFile transcript) throws IOException {
        // This uploads the raw transcript to the DB and stores the id of the raw transcript in reponse.
        ResponseEntity response = new ResponseEntity<>(transcriptService.addTranscript(transcript), HttpStatus.OK);
                
        // Transcript here is of type MultipartFile and is coming directly from the @RequestParam.
        WozTranscriptParser parser = new WozTranscriptParser();

        ArrayList<HashMap<String, ArrayList<Dialogue<WozMessage>>>> parsedTranscript = parser.parseTranscript(transcript);

        // Code to classify the talksteps on parsedTranscript will be here.
        // return parsedTranscript;
        return response;

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