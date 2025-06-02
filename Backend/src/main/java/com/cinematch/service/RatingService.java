package com.cinematch.service;

import com.cinematch.dto.MovieWithRatingDTO;
import com.cinematch.dto.RatingDTO;
import com.cinematch.entity.Movie;
import com.cinematch.entity.Rating;
import com.cinematch.entity.User;
import com.cinematch.entity.WatchlistItem;
import com.cinematch.repository.MovieRepository;
import com.cinematch.repository.RatingRepository;
import com.cinematch.repository.UserRepository;
import com.cinematch.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private MovieRepository movieRepository;


    public List<Rating> getUserRatings(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return ratingRepository.findByUserId(user.getId());
    }

    public void saveOrUpdateRating(String email, RatingDTO dto) {
        User user = userRepository.findByEmail(email).orElseThrow();

        Rating rating = ratingRepository
                .findByUserIdAndMovieId(user.getId(), dto.movieId)
                .orElse(new Rating());

        rating.setUserId(user.getId());
        rating.setMovieId(dto.movieId);
        rating.setRating(dto.rating);
        rating.setComment(dto.comment);
        rating.setCreatedAt(LocalDateTime.now());

        ratingRepository.save(rating);
    }
    public List<MovieWithRatingDTO> getWatchlistMoviesWithRatings(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();

        List<WatchlistItem> watchlist = watchlistRepository.findByUserId(user.getId());
        List<MovieWithRatingDTO> result = new ArrayList<>();

        for (WatchlistItem item : watchlist) {
            Movie movie = movieRepository.findById(item.getMovieId()).orElse(null);
            if (movie == null) continue;

            Optional<Rating> ratingOpt = ratingRepository.findByUserIdAndMovieId(user.getId(), movie.getId());
            Rating rating = ratingOpt.orElse(null);

            MovieWithRatingDTO dto = new MovieWithRatingDTO();
            dto.setId(movie.getId());
            dto.setTitle(movie.getTitle());
            dto.setPosterUrl(movie.getPosterUrl());
            dto.setRating(rating != null ? rating.getRating() : 0);
            dto.setComment(rating != null ? rating.getComment() : "");

            result.add(dto);
        }

        return result;
    }

}
