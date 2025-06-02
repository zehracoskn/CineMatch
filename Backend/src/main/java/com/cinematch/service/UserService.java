package com.cinematch.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import com.cinematch.dto.*;
import com.cinematch.entity.*;
import com.cinematch.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserPreferenceRepository preferenceRepository;
    private final WatchlistRepository watchlistRepository;
    private final WatchedMovieRepository watchedMovieRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserPreferenceRepository preferenceRepository,
                       WatchlistRepository watchlistRepository,
                       WatchedMovieRepository watchedMovieRepository) {
        this.userRepository = userRepository;
        this.preferenceRepository = preferenceRepository;
        this.watchlistRepository = watchlistRepository;
        this.watchedMovieRepository = watchedMovieRepository;
    }

    public String registerUser(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists.";
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // Şimdilik hash yok

        user.setGender(request.getGender());

        userRepository.save(user);

        return "User registered successfully.";
    }

    public String addPreference(PreferenceRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user == null) {
            return "User not found.";
        }

        UserPreference pref = new UserPreference();
        pref.setGenre(request.getGenre());
        pref.setActor(request.getActor());
        pref.setDirector(request.getDirector());
        pref.setUser(user);

        user.getPreferences().add(pref);
        userRepository.save(user); // cascade ile preferences da kaydolur

        return "Preference added.";
    }

    public String updatePreference(PreferenceUpdateRequest req) {
        UserPreference pref = preferenceRepository.findById(req.getPrefId())
                .orElseThrow(() -> new RuntimeException("Pref not found"));
        if (!pref.getUser().getEmail().equals(req.getEmail()))
            return "Invalid owner.";

        pref.setGenre(req.getGenre());
        pref.setActor(req.getActor());
        pref.setDirector(req.getDirector());
        return "Preference updated.";
    }

    public String deletePreference(String email, Long prefId) {
        UserPreference pref = preferenceRepository.findById(prefId)
                .orElseThrow(() -> new RuntimeException("Pref not found"));
        if (!pref.getUser().getEmail().equals(email))
            return "Invalid owner.";
        preferenceRepository.delete(pref);
        return "Preference deleted.";
    }
    public String saveAllPreferences(PreferenceGroupRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UserPreference> newPrefs = new ArrayList<>();

        if (request.getGenres() != null) {
            for (String genre : request.getGenres()) {
                UserPreference p = new UserPreference();
                p.setGenre(genre);
                p.setUser(user);
                newPrefs.add(p);
            }
        }

        if (request.getActors() != null) {
            for (String actor : request.getActors()) {
                UserPreference p = new UserPreference();
                p.setActor(actor);
                p.setUser(user);
                newPrefs.add(p);
            }
        }

        if (request.getDirectors() != null) {
            for (String director : request.getDirectors()) {
                UserPreference p = new UserPreference();
                p.setDirector(director);
                p.setUser(user);
                newPrefs.add(p);
            }
        }

        user.getPreferences().addAll(newPrefs);
        userRepository.save(user); // cascade ile preferences da kaydolur

        return "All preferences saved.";
    }
    public String saveWatchedMovie(WatchedMovieRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (req.getMovieId() == null) throw new RuntimeException("Movie ID cannot be null");

        WatchedMovie movie = new WatchedMovie();
        movie.setMovieId(req.getMovieId());
        movie.setTitle(req.getTitle());
        movie.setLiked(req.isLiked());
        movie.setWatchedAt(LocalDateTime.now());
        movie.setUser(user);

        watchedMovieRepository.save(movie);

        return "Movie saved as watched";
    }


    public List<UserPreference> getPreferences(String email) {
        return userRepository.findByEmail(email)
                .map(User::getPreferences)
                .orElse(Collections.emptyList());
    }

    public String addToWatchlist(WatchlistRequest req) {
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        WatchlistItem item = new WatchlistItem();
        item.setMovieId(req.getMovieId());
        item.setTitle(req.getTitle());
        item.setUser(user);
        watchlistRepository.save(item);
        if(user.getWatchlist() == null){
            user.setWatchlist(new ArrayList<>());
        }

        user.getWatchlist().add(item);
        return "Movie added to watchlist.";
    }

    public String markAsWatched(String email, Long watchlistId) {
        WatchlistItem item = watchlistRepository.findById(watchlistId)
                .orElseThrow(() -> new RuntimeException("Watchlist item not found."));
        if (!item.getUser().getEmail().equals(email)) {
            return "Invalid owner.";
        }
        WatchedMovie watched = new WatchedMovie();
        watched.setMovieId(item.getMovieId());
        watched.setTitle(item.getTitle());
        watched.setUser(item.getUser());
        watchedMovieRepository.save(watched);
        watchlistRepository.delete(item);
        return "Movie marked as watched.";
    }

    public List<WatchlistItem> getWatchlist(String email) {
        return userRepository.findByEmail(email)
                .map(User::getWatchlist)
                .orElse(Collections.emptyList());
    }

    public List<WatchedMovie> getWatched(String email) {
        return userRepository.findByEmail(email)
                .map(User::getWatchedMovies)
                .orElse(Collections.emptyList());
    }

    public String changePassword(ChangePasswordRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(req.getOldPassword())) {
            return "Incorrect current password.";
        }

        user.setPassword(req.getNewPassword());
        userRepository.save(user);
        return "Password updated successfully.";
    }
    public String updateGender(GenderUpdateRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setGender(req.getGender());
        userRepository.save(user);
        return "Gender updated.";
    }

    public String updateUserInfo(UpdateUserRequest req) {
        Optional<User> optionalUser = userRepository.findByEmail(req.getCurrentEmail());

        if (optionalUser.isEmpty()) {
            return "User not found.";
        }

        User user = optionalUser.get();
        
        // Aynı email'de değişiklik varsa çakışma kontrolü
        if (!req.getCurrentEmail().equals(req.getNewEmail()) &&
            userRepository.findByEmail(req.getNewEmail()).isPresent()) {
            return "New email already in use.";
        }

        user.setEmail(req.getNewEmail());
        user.setFullName(req.getNewFullName());
        userRepository.save(user);

        return "User info updated successfully.";
    }


    public User getProfile(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
