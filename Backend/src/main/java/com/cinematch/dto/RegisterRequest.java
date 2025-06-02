package com.cinematch.dto;

import java.util.List;

public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private String gender; // EKLENDİ
    private List<String> preferredGenres;
    private List<String> preferredActors;
    private List<String> preferredDirectors;

    // Getters & Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getGender() { return gender; } // EKLENDİ
    public void setGender(String gender) { this.gender = gender; } // EKLENDİ

    public List<String> getPreferredGenres() { return preferredGenres; }
    public void setPreferredGenres(List<String> preferredGenres) { this.preferredGenres = preferredGenres; }

    public List<String> getPreferredActors() { return preferredActors; }
    public void setPreferredActors(List<String> preferredActors) { this.preferredActors = preferredActors; }

    public List<String> getPreferredDirectors() { return preferredDirectors; }
    public void setPreferredDirectors(List<String> preferredDirectors) { this.preferredDirectors = preferredDirectors; }
}
