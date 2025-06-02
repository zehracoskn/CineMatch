package com.cinematch.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String gender; // EKLENDİ

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<UserPreference> preferences;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WatchlistItem> watchlist = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WatchedMovie> watchedMovies = new ArrayList<>();

    @ElementCollection
    private List<String> preferredGenres = new ArrayList<>();

    @ElementCollection
    private List<String> preferredActors = new ArrayList<>();

    @ElementCollection
    private List<String> preferredDirectors = new ArrayList<>();

    public User() {}

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() { // EKLENDİ
        return gender;
    }

    public void setGender(String gender) { // EKLENDİ
        this.gender = gender;
    }

    public List<UserPreference> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<UserPreference> preferences) {
        this.preferences = preferences;
    }

    public List<WatchlistItem> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(List<WatchlistItem> watchlist) {
        this.watchlist = watchlist;
    }

    public List<WatchedMovie> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(List<WatchedMovie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public List<String> getPreferredGenres() {
        return preferredGenres;
    }

    public void setPreferredGenres(List<String> preferredGenres) {
        this.preferredGenres = preferredGenres;
    }

    public List<String> getPreferredActors() {
        return preferredActors;
    }

    public void setPreferredActors(List<String> preferredActors) {
        this.preferredActors = preferredActors;
    }

    public List<String> getPreferredDirectors() {
        return preferredDirectors;
    }

    public void setPreferredDirectors(List<String> preferredDirectors) {
        this.preferredDirectors = preferredDirectors;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
