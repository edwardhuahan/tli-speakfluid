package com.speakfluid.backend.usecases;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.speakfluid.backend.entities.json.*;
import com.speakfluid.backend.entities.message.Transcript;
import com.speakfluid.backend.entities.message.Dialogue;
import com.speakfluid.backend.entities.message.DialogueList;
import com.speakfluid.backend.entities.message.WozMessage;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * Temporary file for parse method
 * 
 * @author  Minh Le, Edward Han
 * @version 1.0
 * @since   2022-11-15
 */
public class WozTranscriptParser {

    /**
     * Used specfically to deserialize MultiWoz Transcript json files with the following structure:
     * { filename1:
     *      {"log": [
     *          { "text": message1,
     *            "metadata" : {}
     *          },
     *          { "text": message2,
     *            "metadata" : {}
     *          }
     *              ]
     *          }
     *      },
     *   filename2:
     *   ...
     * }
     *
     * @param node Created from reading a multipart file or regular filepath
     * @return an ArrayList<JSONTranscript> object, which is a plain old java object (POJO)
     * representation of the MultiWoz JSON.
     */
    public ArrayList<JSONTranscript> deserialize(ObjectNode node) throws JsonProcessingException {

        ArrayList<JSONTranscript> transcripts = new ArrayList<>();
        Iterator<String> iterator = node.fieldNames();
        ObjectMapper mapper = new ObjectMapper();

        // Iterate through, because fieldNames only returns an iterator
        while (iterator.hasNext()) {
            String fileKey = iterator.next();
            JsonNode data = node.get(fileKey);
            JsonNode logs = data.get("log");
            String logString = logs.toString();

            ArrayList<JSONDialogue> deserializedData = mapper.readValue(logString, new TypeReference<>() {});
            JSONTranscript dt = new JSONTranscript(fileKey, deserializedData);
            transcripts.add(dt);
        }

        return transcripts;
    }

    /**
     * Used for deserializing specifically Spring file objects
     * @param file used to store a Spring file object inputted by the user
     * @return an ArrayList<JSONTranscript> object, by calling deserialize()
     */
    public ArrayList<JSONTranscript> deserializeMultifile(MultipartFile file) {
        ArrayList<JSONTranscript> transcripts = new ArrayList<>();

        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));

            // create mapper of json file
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = mapper.readValue(fileReader, ObjectNode.class);

            transcripts = deserialize(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transcripts;
    }

    /**
     * Used for deserializing specifically file objects that are referenced with a string
     * @param file used to store a filepath string inputted by the user
     * @return an ArrayList<JSONTranscript> object, by calling deserialize()
     */
    public ArrayList<JSONTranscript> deserializeFilepath(String file) {
        ArrayList<JSONTranscript> transcripts = new ArrayList<>();

        try {
            // create mapper of json file
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = mapper.readValue(new File(file), ObjectNode.class);

            transcripts = deserialize(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transcripts;
    }

    public ArrayList<Transcript> parse(ArrayList<JSONTranscript> transcripts) {
        ArrayList<Transcript> result = new ArrayList<>();
        for (JSONTranscript transcript : transcripts) {
            Transcript conversation = new Transcript();

            DialogueList dialogueList = parseTranscript(transcript);

            conversation.put(transcript.getFileName(), dialogueList);
            result.add(conversation);
        }

        return result;
    }

    /**
     * Used for parsing a singular transcript into appropriate data.
     * @param jsonTranscript a singular transcript object
     * @return a DialogueList, containing information relevant to the Dialogue between
     * user and chatbot
     */
    private DialogueList parseTranscript(JSONTranscript jsonTranscript) {

        DialogueList dialogueList = new DialogueList();

        ArrayList<WozMessage> userMessage = new ArrayList<WozMessage>();
        ArrayList<WozMessage> chatbotMessage = new ArrayList<WozMessage>();

        ArrayList<JSONDialogue> logs = jsonTranscript.getLogs();
        int numMessages = logs.size();

        // iterate through list of messages in one conversation,
        // updating userMessage and chatbotMessage
        // and adding to dialogueList
        for (int i = 0; i < numMessages; i++) {

            JSONDialogue current_message = logs.get(i);
            boolean isHuman = current_message.getMetadata().isEmpty();
            WozMessage message = new WozMessage("response", current_message.getText());

            if (isHuman) {
                userMessage.add(message);
            } else {
                chatbotMessage.add(message);
            }

            // if current message is the last message in conversation
            // or if ext message is sent by chatbot
            if (i == numMessages - 1 || !logs.get(i + 1).getMetadata().isEmpty()) {
                dialogueList.add(new Dialogue<WozMessage>(chatbotMessage, userMessage));
                chatbotMessage = new ArrayList<>();
                userMessage = new ArrayList<>();
            }

        }
        return dialogueList;
    }
    
}
