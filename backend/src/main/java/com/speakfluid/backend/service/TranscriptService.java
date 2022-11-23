package com.speakfluid.backend.service;

import com.speakfluid.backend.model.TranscriptLoader;
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

/**
 * TranscriptService is used to query MongoDB.
 *
 * @author  Kai Zhuang
 * @version 1.0
 * @since   2022-11-12
 */

@Service
public class TranscriptService {

    @Autowired
    private GridFsTemplate template;

    @Autowired
    private GridFsOperations operations;

    /**
     * addTranscript() takes in a MultipartFile object and stores it in the DB
     */
    public String addTranscript(MultipartFile upload) throws IOException {

        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", upload.getSize());

        Object fileID = template.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(), metadata);

        return fileID.toString();
    }

    /**
     * downloadTranscript() takes in an id of a transcript and uses that
     * retrieve and return the corresponding transcript in the DB.
     */
    public TranscriptLoader downloadTranscript(String id) throws IOException {

        GridFSFile gridFSTranscript = template.findOne( new Query(Criteria.where("_id").is(id)) );

        TranscriptLoader transcriptLoader = new TranscriptLoader();

        if (gridFSTranscript != null && gridFSTranscript.getMetadata() != null) {
            transcriptLoader.setTranscriptname( gridFSTranscript.getFilename() );

            transcriptLoader.setTranscriptType( gridFSTranscript.getMetadata().get("_contentType").toString() );

            transcriptLoader.setTranscriptSize( gridFSTranscript.getMetadata().get("fileSize").toString() );

            transcriptLoader.setTranscript( IOUtils.toByteArray(operations.getResource(gridFSTranscript).getInputStream()) );
        }

        return transcriptLoader;
    }

}