package com.mdbspringboot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mdbspringboot.model.Transcript;

public interface ItemRepository extends MongoRepository<Transcript, String> {
    // This is the API implementation. This acts as a link between the MongoDB model and the database.

    // findItemByName is a query, we specify the parameter it takes with @Query. This searches through the collection
    // and returns the first item it finds with the matching name.
    @Query("{name:'?0'}")
    Transcript findItemByName(String name);

    // We use count to count how long the collection is.
    public long count();

}
