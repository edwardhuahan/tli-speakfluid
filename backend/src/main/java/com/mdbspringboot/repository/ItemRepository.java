package com.mdbspringboot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mdbspringboot.model.Transcript;

public interface ItemRepository extends MongoRepository<Transcript, String> {

    @Query("{name:'?0'}")
    Transcript findItemByName(String name);

    public long count();

}
