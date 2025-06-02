package com.cinematch.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "watched_movies")
public class WatchedMovie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long movieId;
    private boolean liked;
    private String title;
    private LocalDateTime watchedAt = LocalDateTime.now();

    @ManyToOne @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    public WatchedMovie() {}

    // getters & setters
    public Long getId() { return id; }

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDateTime getWatchedAt() { return watchedAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public boolean isLiked() {
        return liked;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public void setWatchedAt(LocalDateTime watchedAt) {
        this.watchedAt = watchedAt;
    }


}
