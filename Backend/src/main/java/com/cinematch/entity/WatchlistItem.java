// entity/WatchlistItem.java
package com.cinematch.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "watchlist")
public class WatchlistItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long movieId;
    private String title;
    private LocalDateTime addedAt = LocalDateTime.now();

    @ManyToOne @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    public WatchlistItem() {}

    // getters & setters
    public Long getId() { return id; }

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDateTime getAddedAt() { return addedAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

}
