package com.cinematch.controller;

import com.cinematch.entity.Movie;
import com.cinematch.entity.User;
import com.cinematch.repository.UserRepository;
import com.cinematch.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recommend")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/instant-pick")
    public Movie getInstantPick(@RequestParam String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return recommendationService.getInstantPick(user);
    }


    @GetMapping("/match-list")
    public List<Movie> getMatchedMovies(@RequestParam String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return recommendationService.getMoviesWithMatchPercent(user.getId()).stream()
                .filter(m -> m.getMatchPercent() != null)
                .sorted(Comparator.comparingInt(Movie::getMatchPercent).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
    @GetMapping("/swipe")
    public List<Movie> getSwipeRecommendations(@RequestParam String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return recommendationService.getKnnRecommendations(user);
    }


}
