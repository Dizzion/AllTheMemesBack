package com.memes.backend.repository;


import com.memes.backend.model.Memes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface MemesRepository extends MongoRepository<Memes, String> {
    Memes findByDataPosted(Date date);
}
