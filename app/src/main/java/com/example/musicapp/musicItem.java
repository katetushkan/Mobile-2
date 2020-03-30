package com.example.musicapp;

import com.google.firebase.firestore.Exclude;

public class musicItem {

    private String imageUrl;
    private String artistName;
    private String year;
    private String trailer;
    private String albumName;
    private String description;
    private String documentId;
    private String descriptionRus;

    public String getDescriptionRus() {
        return descriptionRus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }
    public String getTrailer() {
        return trailer;
    }

    public String getAlbumName() {
        return albumName;
    }
    public musicItem(){

    }

    public musicItem(String imageUrl, String artistName, String year, String albumName, String description, String descriptionRus, String trailer) {
        this.imageUrl = imageUrl;
        this.artistName = artistName;
        this.year = year;
        this.trailer = trailer;
        this.description = description;
        this.descriptionRus = descriptionRus;
        this.albumName = albumName;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

}
