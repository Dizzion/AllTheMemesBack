package com.memes.backend.controller;

import com.memes.backend.model.Memes;
import com.memes.backend.repository.MemesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "api/v1")
public class MemesController {

    @Autowired
    private MemesRepository memesRepository;

    @GetMapping("/")
    public ResponseEntity<List<Memes>> getTrending() {
        try {
            List<Memes> memes = new ArrayList<Memes>();
            List<Memes> trendMemes = new ArrayList<Memes>();
            memesRepository.findAll().forEach(memes::add);

            if (memes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            memes.forEach(meme -> {
                if (meme.isTrending()) {
                    trendMemes.add(meme);
                }
            });
            return new ResponseEntity<>(trendMemes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
