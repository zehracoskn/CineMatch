package com.cinematch.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public class PreferenceUpdateRequest {
    @Email @NotBlank
    private String email;     // hangi kullanıcı?
    private Long  prefId;     // güncellenecek / silinecek satır
    private String genre;
    private String actor;
    private String director;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getPrefId() { return prefId; }
    public void setPrefId(Long prefId) { this.prefId = prefId; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getActor() { return actor; }
    public void setActor(String actor) { this.actor = actor; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
}

