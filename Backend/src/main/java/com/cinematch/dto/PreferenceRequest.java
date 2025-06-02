package com.cinematch.dto;

public class PreferenceRequest {
    private String email;
    private String genre;
    private String actor;
    private String director;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getActor() { return actor; }
    public void setActor(String actor) { this.actor = actor; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
}
