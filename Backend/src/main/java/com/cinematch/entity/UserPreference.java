package com.cinematch.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "user_preferences")
public class UserPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String genre;
    private String actor;
    private String director;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;


    public UserPreference() {}

    // Getters & Setters
    public Long getId() { return id; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getActor() { return actor; }
    public void setActor(String actor) { this.actor = actor; }
    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
