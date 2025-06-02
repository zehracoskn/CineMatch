package com.cinematch.controller;

import com.cinematch.dto.RatingDTO;
import com.cinematch.entity.Rating;
import com.cinematch.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public List<Rating> getUserRatings(@RequestParam String email) {
        return ratingService.getUserRatings(email);
    }

    @PostMapping
    public ResponseEntity<?> saveRating(@RequestParam String email, @RequestBody RatingDTO dto) {
        ratingService.saveOrUpdateRating(email, dto);
        return ResponseEntity.ok().build();
    }
}

