package com.speakfluid.backend.service;

import com.speakfluid.backend.model.LoadTranscript;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class TranscriptService {

    @Autowired
    private GridFsTemplate template;

    @Autowired
    private GridFsOperations operations;

    public String addTranscript(MultipartFile upload) throws IOException {

        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", upload.getSize());

        Object fileID = template.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(), metadata);

        return fileID.toString();
    }

    public LoadTranscript downloadTranscript(String id) throws IOException {

        GridFSFile gridFSTranscript = template.findOne( new Query(Criteria.where("_id").is(id)) );

        LoadTranscript loadTranscript = new LoadTranscript();

        if (gridFSTranscript != null && gridFSTranscript.getMetadata() != null) {
            loadTranscript.setTranscriptname( gridFSTranscript.getFilename() );

            loadTranscript.setTranscriptType( gridFSTranscript.getMetadata().get("_contentType").toString() );

            loadTranscript.setTranscriptSize( gridFSTranscript.getMetadata().get("fileSize").toString() );

            loadTranscript.setTranscript( IOUtils.toByteArray(operations.getResource(gridFSTranscript).getInputStream()) );
        }

        return loadTranscript;
    }

}