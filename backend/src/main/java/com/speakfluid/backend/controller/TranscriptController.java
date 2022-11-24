package com.speakfluid.backend.controller;


import com.speakfluid.backend.entities.Dialogue;
import com.speakfluid.backend.entities.WozMessage;

import com.speakfluid.backend.model.TranscriptLoader;

import com.speakfluid.backend.service.TranscriptService;
import com.speakfluid.backend.usecases.TranscriptAnalysisInteractor;

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

/**
 * TranscriptController is used as an API
 * to communicate raw transcript CRUD operations 
 * with the frontend.
 *
 * @author  Kai Zhuang
 * @version 1.0
 * @since   2022-11-12
 */

@RestController
@CrossOrigin("*")
@RequestMapping("transcript")
public class TranscriptController {

    @Autowired
    private TranscriptService transcriptService;

    /**
     * upload() requests a file from the API caller and stores that file in 
     * the DB using TransriptService's addTranscript()
     */
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("transcript")MultipartFile transcript) throws IOException {
        // This uploads the raw transcript to the DB and stores the id of the raw transcript in reponse.
        String rawTranscriptId = transcriptService.addTranscript(transcript);
                
        // Transcript here is of type MultipartFile and is coming directly from the @RequestParam.
        WozTranscriptParser parser = new WozTranscriptParser();

        ArrayList<HashMap<String, ArrayList<Dialogue<WozMessage>>>> parsedTranscript = parser.parseTranscript(transcript);

        // Code to classify the talksteps on parsedTranscript will be here.
        TranscriptAnalysisInteractor interactor = new TranscriptAnalysisInteractor();
        ArrayList<HashMap<String, ArrayList<Dialogue<WozMessage>>>> analyzedTranscript = interactor.analyzeTranscript(parsedTranscript);

        // return parsedTranscript;
        return new ResponseEntity<>(analyzedTranscript, HttpStatus.OK);

    }

    /**
     * download() receives a path variable id from the API caller
     * and uses TranscriptService's downloadTranscript to query the DB
     * and retrieve the correct raw transcript. It then returns the transcript
     * as a TranscriptLoader.
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        TranscriptLoader transcriptLoader = transcriptService.downloadTranscript(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(transcriptLoader.getTranscriptType() ))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; transcriptname=\"" + transcriptLoader.getTranscriptName() + "\"")
                .body(new ByteArrayResource(transcriptLoader.getTranscript()));
    }

}