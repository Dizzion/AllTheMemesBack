package com.memes.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "Memes")
@Getter @Setter @NoArgsConstructor
public class Memes {

    @Id
    private String id;

    private String url;
    private Date addedDate;
    private int disLikes;
    private int likes;
    private boolean isTrending;

    @DBRef
    private List<Comments> comments;

    public Memes(String url, Date addedDate, int disLikes, int likes, boolean isTrending) {
        this.url = url;
        this.addedDate = addedDate;
        this.disLikes = disLikes;
        this.likes = likes;
        this.isTrending = isTrending;
    }
}
