package com.cinematch.repository;

import com.cinematch.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByUserId(Long userId);
    Optional<Rating> findByUserIdAndMovieId(Long userId, Long movieId);
}

