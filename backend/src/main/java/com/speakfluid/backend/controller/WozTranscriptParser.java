package com.speakfluid.backend.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.speakfluid.backend.entities.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;

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

            ArrayList<JSONLog> deserializedData = mapper.readValue(logString, new TypeReference<>() {});
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

    // TEMPORARY RETURN TYPE
    public ArrayList<HashMap<String, ArrayList<Dialogue<WozMessage>>>> parse(ArrayList<JSONTranscript> transcripts) {
        ArrayList<HashMap<String, ArrayList<Dialogue<WozMessage>>>> result = new ArrayList<>();
        for (JSONTranscript transcript : transcripts) {
            Conversation conversation = new Conversation();

            DialogueList dialogueList = parseTranscript(transcript);

            conversation.put(transcript.getFileName(), dialogueList);
            result.add(conversation.getMap());
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

        ArrayList<JSONLog> logs = jsonTranscript.getLogs();
        int numMessages = logs.size();

        // iterate through list of messages in one conversation,
        // updating userMessage and chatbotMessage
        // and adding to dialogueList
        for (int i = 0; i < numMessages; i++) {

            JSONLog current_message = logs.get(i);
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

//     public static void main(String[] args) {
//         WozTranscriptParser to_parse = new WozTranscriptParser();
//         ArrayList<HashMap<String, ArrayList<Dialogue>>> parsedTranscript =
//         to_parse.parseTranscript("/Users/minhle/Documents/2nd Year/TLI/tli-speakfluid/data.json");
//
//         // how to iterature through parsedTranscript
//         for (HashMap<String, ArrayList<Dialogue>> conversation : parsedTranscript) {
//             if (conversation.containsKey("SNG0827.json")) {
//                 ArrayList<Dialogue> dialogueList = conversation.get("SNG0827.json");
//                 for (Dialogue dialogue : dialogueList) {
//
//
//                     System.out.println("new dialogue");
//                     for (WozMessage chatbotM : dialogue.getChatBotMessage()) {
//
//                         System.out.println(chatbotM.getMessage());
//                     }
//                     for (WozMessage userM : dialogue.getUserMessage()) {
//
//                         System.out.println(userM.getMessage());
//                     }
//                 }
//             }
//         }
//
//     }
    
}
