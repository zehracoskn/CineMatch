package com.cinematch.controller;

import com.cinematch.dto.*;
import com.cinematch.entity.*;
import com.cinematch.repository.MovieRepository;
import com.cinematch.repository.WatchedMovieRepository;
import com.cinematch.service.RatingService;
import com.cinematch.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")

public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private MovieRepository movieRepository;





    @PostMapping("/user/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String result = userService.registerUser(request);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/preferences")
    public ResponseEntity<String> addPreference(@RequestBody PreferenceRequest request) {
        String result = userService.addPreference(request);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/preferences/batch")
    public ResponseEntity<String> saveAllPreferences(@RequestBody PreferenceGroupRequest request) {
        return ResponseEntity.ok(userService.saveAllPreferences(request));
    }


    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@RequestParam String email) {
        User user = userService.getProfile(email);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
    /* ---------- PREFERENCES ---------- */
    @PutMapping("/preferences")
    public ResponseEntity<String> updatePreference(@RequestBody PreferenceUpdateRequest req) {
        String result = userService.updatePreference(req);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/preferences")
    public ResponseEntity<String> deletePref(@RequestParam String email,
                                             @RequestParam Long id) {
        return ResponseEntity.ok(userService.deletePreference(email, id));
    }
    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return movieRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/preferences")
    public ResponseEntity<List<UserPreference>> listPref(@RequestParam String email) {
        return ResponseEntity.ok(userService.getPreferences(email));
    }

    /* ---------- WATCHLIST ---------- */
    @PostMapping("/watchlist")
    public ResponseEntity<String> addWatch(@Valid @RequestBody WatchlistRequest r) {
        String result =  userService.addToWatchlist(r);
        return ResponseEntity.ok(result);
    }
    @PatchMapping("/watchlist/{id}/watched")
    public ResponseEntity<String> markWatched(@RequestParam String email,
                                              @PathVariable Long id) {
        return ResponseEntity.ok(userService.markAsWatched(email, id));
    }
    @GetMapping("/ratings/from-watchlist")
    public List<MovieWithRatingDTO> getWatchlistRatings(@RequestParam String email) {
        return ratingService.getWatchlistMoviesWithRatings(email);
    }
    @GetMapping("/watchlist")
    public ResponseEntity<List<WatchlistItem>> getWatchlist(@RequestParam String email) {
        return ResponseEntity.ok(userService.getWatchlist(email));
    }
    @GetMapping("/watched")
    public ResponseEntity<List<WatchedMovie>> getWatched(@RequestParam String email) {
        return ResponseEntity.ok(userService.getWatched(email));
    }
    @PostMapping("/watched")
    public ResponseEntity<String> markWatchedMovie(@RequestBody WatchedMovieRequest req) {
        try {
            return ResponseEntity.ok(userService.saveWatchedMovie(req));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }


    @PostMapping("/preferences/delete")
    public ResponseEntity<String> deletePref(@RequestBody PreferenceUpdateRequest req) {
        String result = userService.deletePreference(req.getEmail(), req.getPrefId());
        return ResponseEntity.ok(result);
    }
    @PatchMapping("/user/password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest req) {
        return ResponseEntity.ok(userService.changePassword(req));
    }
    @PatchMapping("/user/gender")
    public ResponseEntity<String> updateGender(@RequestBody GenderUpdateRequest req) {
        return ResponseEntity.ok(userService.updateGender(req));
    }
    
    /* ---------- USER ---------- */
    
    @PutMapping("/user/update")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserRequest request) {
        String result = userService.updateUserInfo(request);
        return ResponseEntity.ok(result);
    }

}


