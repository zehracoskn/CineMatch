package com.cinematch.service;

import com.cinematch.entity.Movie;
import com.cinematch.entity.User;
import com.cinematch.entity.WatchedMovie;
import com.cinematch.repository.MovieRepository;
import com.cinematch.repository.WatchedMovieRepository;
import com.cinematch.ai.KNNRecommender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cinematch.entity.UserPreference;
import com.cinematch.repository.UserRepository;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Objects;

@Service
public class RecommendationService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;



    @Autowired
    private WatchedMovieRepository watchedMovieRepository;

    public List<Movie> getMoviesWithMatchPercent(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        List<Movie> allMovies = movieRepository.findAll();

        // Kullanıcı tercihlerini türlere göre ayır
        Set<String> userGenres = user.getPreferences().stream()
                .map(UserPreference::getGenre)
                .filter(Objects::nonNull)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        Set<String> userActors = user.getPreferences().stream()
                .map(UserPreference::getActor)
                .filter(Objects::nonNull)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        Set<String> userDirectors = user.getPreferences().stream()
                .map(UserPreference::getDirector)
                .filter(Objects::nonNull)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        for (Movie movie : allMovies) {
            // GENRE eşleşmesi
            Set<String> movieGenres = Arrays.stream(
                            Optional.ofNullable(movie.getGenres()).orElse("")
                                    .toLowerCase()
                                    .split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toSet());
            Set<String> commonGenres = new HashSet<>(movieGenres);
            commonGenres.retainAll(userGenres);

            int genrePercent = 0;
            if (!userGenres.isEmpty()) {
                genrePercent = (int) Math.round(((double) commonGenres.size() / userGenres.size()) * 100);
            }

            // ACTOR eşleşmesi
            Set<String> movieActors = Arrays.stream(
                            Optional.ofNullable(movie.getActors()).orElse("")
                                    .toLowerCase()
                                    .split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toSet());
            Set<String> commonActors = new HashSet<>(movieActors);
            commonActors.retainAll(userActors);

            int actorPercent = 0;
            if (!userActors.isEmpty()) {
                actorPercent = (int) Math.round(((double) commonActors.size() / userActors.size()) * 100);
            }

            // DIRECTOR eşleşmesi
            Set<String> movieDirectors = new HashSet<>();
            String directorsStr = Optional.ofNullable(movie.getDirector()).orElse("").toLowerCase();
            if (!directorsStr.isEmpty()) {
                movieDirectors = Arrays.stream(directorsStr.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toSet());
            }
            Set<String> commonDirectors = new HashSet<>(movieDirectors);
            commonDirectors.retainAll(userDirectors);

            int directorPercent = 0;
            if (!userDirectors.isEmpty()) {
                directorPercent = (int) Math.round(((double) commonDirectors.size() / userDirectors.size()) * 100);
            }

            // Toplam skor (her kategoriye eşit ağırlık)
            int categoryCount = 0;
            if (!userGenres.isEmpty()) categoryCount++;
            if (!userActors.isEmpty()) categoryCount++;
            if (!userDirectors.isEmpty()) categoryCount++;

            int matchPercent = 0;
            if (categoryCount > 0) {
                matchPercent = (genrePercent + actorPercent + directorPercent) / categoryCount;
            }

            // TABAN PUAN: Minimum %40
            int basePercent = 40;
            matchPercent = basePercent + (int)Math.round(matchPercent * (100.0 - basePercent) / 100.0);

            movie.setMatchPercent(matchPercent);
        }

        return allMovies;
    }

    public Movie getInstantPick(User user) {
        List<WatchedMovie> likedHistory = watchedMovieRepository.findByUserIdAndLiked(user.getId(), true);

        List<Movie> likedMovies = likedHistory.stream()
                .map(wm -> movieRepository.findById(wm.getMovieId()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Movie> allMovies = movieRepository.findAll();

        KNNRecommender recommender = new KNNRecommender(allMovies);
        List<Movie> results = recommender.recommend(likedMovies, 1);

        return results.isEmpty() ? null : results.get(0);
    }
    public List<Movie> getKnnRecommendations(User user) {
        List<WatchedMovie> likedHistory = watchedMovieRepository.findByUserIdAndLiked(user.getId(), true);

        List<Movie> likedMovies = likedHistory.stream()
                .map(wm -> movieRepository.findById(wm.getMovieId()).orElse(null))
                .filter(Objects::nonNull)
                .toList();

        List<Movie> allMovies = movieRepository.findAll();
        KNNRecommender recommender = new KNNRecommender(allMovies);

        // Örneğin 20 film döndürmek istiyorsan
        return recommender.recommend(likedMovies, 20);
    }


    public Movie getInstantPickById(Long userId) {
        List<WatchedMovie> likedHistory = watchedMovieRepository.findByUserIdAndLiked(userId, true);

        List<Movie> likedMovies = likedHistory.stream()
                .map(wm -> movieRepository.findById(wm.getMovieId()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Movie> allMovies = movieRepository.findAll();
        KNNRecommender recommender = new KNNRecommender(allMovies);
        List<Movie> results = recommender.recommend(likedMovies, 1);

        return results.isEmpty() ? null : results.get(0);
    }

}
