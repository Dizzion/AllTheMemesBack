package com.memes.backend.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "Memes")
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public int getDisLikes() {
        return disLikes;
    }

    public void setDisLikes(int disLikes) {
        this.disLikes = disLikes;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isTrending() {
        return isTrending;
    }

    public void setTrending(boolean trending) {
        isTrending = trending;
    }
}
