package com.speakfluid.backend.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

import com.speakfluid.backend.entities.Conversation;
import com.speakfluid.backend.entities.DialogueList;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.speakfluid.backend.entities.Dialogue;
import com.speakfluid.backend.entities.WozMessage;


/**
 * Temporary file for parse method
 * 
 * @author  Minh Le
 * @version 1.0
 * @since   2022-11-15
 */
public class WozTranscriptParser {

    /**
     * Used specfically to parse MultiWoz Transcript json files with the following structure:
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
     * @param transcript uploaded MultipartFile with MultiWoz data format
     * @return an ArrayList<HashMap<String, ArrayList<Dialogue<WozMessage>>>> object, which is a List of Hashmaps, 
     * mapping filename (String) to conversation (ArrayList<Dialogue<WozMessage>>)
     */
    public ArrayList<HashMap<String, ArrayList<Dialogue<WozMessage>>>> parseTranscript(MultipartFile transcript) {

        ArrayList<HashMap<String, ArrayList<Dialogue<WozMessage>>>> parsedTranscript = new ArrayList<>();

        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(transcript.getInputStream(), "UTF-8"));

            // create mapper of json file
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = mapper.readValue(fileReader, ObjectNode.class);

            // create list of file names to be used as identifier of conversations
            List<String> fileNameList = new ArrayList<>();
            Iterator<String> iterator = node.fieldNames();
            iterator.forEachRemaining(fileNameList::add);
                
            int file_count = 0;

            // iterate through all files in list of json files
            for (JsonNode jsonfile : node) {
                
                Conversation conversation = new Conversation();
                
                DialogueList dialogueList = new DialogueList();

                ArrayList<WozMessage> userMessage = new ArrayList<WozMessage>();
                ArrayList<WozMessage> chatbotMessage = new ArrayList<WozMessage>();

                int message_count = jsonfile.get("log").size();

                // iterate through list of messages in one conversation, 
                // updating userMessage and chatbotMessage
                // and adding to dialogueList
                for (int i = 0; i < message_count; i++) {
                    
                    JsonNode current_message = jsonfile.get("log").get(i);
                    boolean isHuman = current_message.get("metadata").isEmpty();
                    WozMessage message = new WozMessage("response", current_message.get("text").toString());

                    if (isHuman) {
                        userMessage.add(message);
                    } else {
                        chatbotMessage.add(message);
                    }

                    // if current message is the last message in conversation
                    // or if ext message is sent by chatbot
                    if (i == message_count - 1 || !jsonfile.get("log").get(i + 1).isEmpty()) {

                        dialogueList.add(new Dialogue<WozMessage>(chatbotMessage, userMessage));
                        chatbotMessage = new ArrayList<>();
                        userMessage = new ArrayList<>();
                    }
                }
                conversation.put(fileNameList.get(file_count), dialogueList);
                parsedTranscript.add(conversation.getMap());
                file_count++;
            }        
            

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return parsedTranscript;
    
    }
 
    // accept String file_path instead of MultipartFile
    public ArrayList<Conversation> parseTranscriptFilePath(String transcript) {

        ArrayList<Conversation> parsedTranscript = new ArrayList<>();


        try {

            // create mapper of json file
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = mapper.readValue(new File(transcript), ObjectNode.class);

            // create list of file names to be used as identifier of conversations
            List<String> fileNameList = new ArrayList<>();
            Iterator<String> iterator = node.fieldNames();
            iterator.forEachRemaining(fileNameList::add);
        
                
            int file_count = 0;

            // iterate through all files in list of json files
            for (JsonNode jsonfile : node) {
                
                Conversation conversation = new Conversation();
                
                DialogueList dialogueList = new DialogueList();

                ArrayList<WozMessage> userMessage = new ArrayList<WozMessage>();
                ArrayList<WozMessage> chatbotMessage = new ArrayList<WozMessage>();

                int message_count = jsonfile.get("log").size();

                // iterate through list of messages in one conversation, 
                // updating userMessage and chatbotMessage
                // and adding to dialogueList
                for (int i = 0; i < message_count; i++) {

                    JsonNode current_message = jsonfile.get("log").get(i);
                    boolean isHuman = current_message.get("metadata").isEmpty();
                    WozMessage message = new WozMessage("response", current_message.get("text").toString());

                    if (isHuman) {
                        userMessage.add(message);
                    } else {
                        chatbotMessage.add(message);
                    }

                    // if current message is the last message in conversation
                    // or if ext message is sent by chatbot
                    if (i == message_count - 1 || !jsonfile.get("log").get(i + 1).isEmpty()) {

                        dialogueList.add(new Dialogue<WozMessage>(chatbotMessage, userMessage));
                        chatbotMessage = new ArrayList<>();
                        userMessage = new ArrayList<>();
                    }
                
                }
                conversation.put(fileNameList.get(file_count), dialogueList);

                parsedTranscript.add(conversation);

                file_count++;

            }        
            

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return parsedTranscript;
    }
    // public static void main(String[] args) {
    //     WozTranscriptParser to_parse = new WozTranscriptParser();
    //     ArrayList<HashMap<String, ArrayList<Dialogue>>> parsedTranscript = 
    //     to_parse.parseTranscript("/Users/minhle/Documents/2nd Year/TLI/tli-speakfluid/data.json");

    //     // how to iterature through parsedTranscript
    //     for (HashMap<String, ArrayList<Dialogue>> conversation : parsedTranscript) {
    //         if (conversation.containsKey("SNG0827.json")) {
    //             ArrayList<Dialogue> dialogueList = conversation.get("SNG0827.json");
    //             for (Dialogue dialogue : dialogueList) {
                    
                    
    //                 System.out.println("new dialogue");
    //                 for (WozMessage chatbotM : dialogue.getChatBotMessage()) {

    //                     System.out.println(chatbotM.getMessage());
    //                 }
    //                 for (WozMessage userM : dialogue.getUserMessage()) {

    //                     System.out.println(userM.getMessage());
    //                 }
    //             }
    //         }
    //     }

    // }
    
}
