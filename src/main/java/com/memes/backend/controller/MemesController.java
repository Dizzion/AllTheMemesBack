package com.memes.backend.controller;

import com.memes.backend.model.Comments;
import com.memes.backend.model.Memes;
import com.memes.backend.repository.CommentsRepository;
import com.memes.backend.repository.MemesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "api/v1")
public class MemesController {

    @Autowired
    private MemesRepository memesRepository;
    @Autowired
    private CommentsRepository commentsRepository;

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
            if (trendMemes.isEmpty()) {
                while (memes.size() > 24) {
                    memes.remove(24);
                }
                return new ResponseEntity<>(memes, HttpStatus.OK);
            }
            return new ResponseEntity<>(trendMemes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/{hashtag}")
    public ResponseEntity<List<Memes>> getMemesByHashTag(@PathVariable("hashtag") String hashTag) {
        try {
            List<Memes> memesList = memesRepository.findAll();
            List<Memes> _meme = new ArrayList<Memes>();
            memesList.forEach(meme -> {
                if (meme.getHashTags().contains(hashTag)) {
                    _meme.add(meme);
                }
            });
            if (memesList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else if (_meme.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(_meme, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Memes> getMemeById(@PathVariable("id") String id) {
        Optional<Memes> memeData = memesRepository.findById(id);

        if (memeData.isPresent()) {
            return new ResponseEntity<>(memeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Comments> createComment(@PathVariable("id") String id, @RequestBody Comments comment) {
        try {
            Comments _comment = commentsRepository.save(new Comments(comment.getBody(), comment.getUserPosted(), id));
            Memes meme = memesRepository.findById(id).get();
            List<Comments> coms = meme.getComments();
            coms.add(_comment);
            meme.setComments(coms);
            memesRepository.save(meme);
            return new ResponseEntity<>(_comment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Memes> createMeme(@RequestBody Memes meme) {
        try {
            Memes _meme = memesRepository.save(new Memes(meme.getUrl(), meme.getHashTags(), meme.getDisLikes(), meme.getLikes(), meme.isTrending()));

            return new ResponseEntity<>(_meme, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Memes> updateMemeById(@PathVariable("id") String id, @RequestBody Memes meme) {
        Optional<Memes> memeData = memesRepository.findById(id);

        if (memeData.isPresent()) {
            Memes _meme = memeData.get();
            _meme.setUrl(meme.getUrl());
            _meme.setHashTags(meme.getHashTags());

            return new ResponseEntity<>(memesRepository.save(_meme), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGameById(@PathVariable("id") String id) {
        try {
            List<Comments> coms = commentsRepository.findByMemeId(id);
            coms.forEach(com -> {
                commentsRepository.delete(com);
            });
            memesRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
