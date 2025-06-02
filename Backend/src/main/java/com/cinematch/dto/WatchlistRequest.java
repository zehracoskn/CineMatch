package com.cinematch.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class WatchlistRequest {
    @Email @NotBlank
    private String email;
    private Long movieId;
    private String title;        // henüz Movie tablon yoksa geçici
    // getters & setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}