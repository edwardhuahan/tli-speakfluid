package com.mdbspringboot;

import com.mdbspringboot.model.Transcript;
import com.mdbspringboot.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableMongoRepositories
@RestController
public class MdbSpringBootApplication implements CommandLineRunner {

    @Autowired
    ItemRepository transcriptRepo;

    public static void main(String[] args) {
        SpringApplication.run(MdbSpringBootApplication.class, args);
    }

    public void run(String... args) {
        createTranscripts();
        showAllGroceryItems();
        getTranscriptByName("Transcript 1");
        deleteTranscript("1");
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "Test", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    // CRUD

    // CREATE
    void createTranscripts() {
        // We would change this to pass in the transcript information to this function and then save
        // it to the collection.
        System.out.println("Data creation started...");
        transcriptRepo.save(new Transcript("1", "Transcript 1"));
        transcriptRepo.save(new Transcript("2", "Transcript 2"));
        System.out.println("Data creation complete...");
    }

    // READ
    // 1. Show all the data
    public void showAllGroceryItems() {
        transcriptRepo.findAll().forEach(item -> System.out.println(getItemDetails(item)));
    }

    // 2. Get transcript by name
    public void getTranscriptByName(String name) {
        System.out.println("Getting item by name: " + name);
        Transcript item = transcriptRepo.findItemByName(name);
        System.out.println(getItemDetails(item));
    }

    // 3. Get count of documents in the collection
    public void findCountOfTranscripts() {
        long count = transcriptRepo.count();
        System.out.println("Number of documents in the collection: " + count);
    }

    // UPDATE

    // DELETE
    public void deleteTranscript(String id) {
        transcriptRepo.deleteById(id);
        System.out.println("Item with id " + id + " deleted...");
    }

    public String getItemDetails(Transcript item) {

        System.out.println("Item Name: " + item.getName());

        return "";
    }
}