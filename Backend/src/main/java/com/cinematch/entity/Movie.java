package com.cinematch.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String overview;

    private String posterUrl;

    @Column(columnDefinition = "TEXT")
    private String genres;

    private Integer releaseYear;

    @Column(columnDefinition = "TEXT")
    private String actors;

    private String director;

    private Integer runtime;
    private Double rating;

    @Transient
    private Integer matchPercent;





    // ---- GETTER & SETTER ----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getOverview() { return overview; }
    public void setOverview(String overview) { this.overview = overview; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }

    public  String getActors() { return actors; }
    public void setActors( String  actors) { this.actors = actors; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getGenres() { return genres; }
    public void setGenres(String genres) { this.genres = genres; }

    public Integer getRuntime() { return runtime; }
    public void setRuntime(Integer runtime) { this.runtime = runtime; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    
    public Integer getMatchPercent() { return matchPercent; }
    public void setMatchPercent(Integer matchPercent) { this.matchPercent = matchPercent; }

}
