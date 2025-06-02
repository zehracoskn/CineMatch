package com.cinematch.controller;

import com.cinematch.entity.Movie;
import com.cinematch.service.MovieApiService;
import com.cinematch.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieImportController {

    @Autowired
    private MovieRepository movieRepository;



    @Autowired
    private MovieApiService movieApiService;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }


    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieRepository.findById(id).orElse(null);
    }


    @GetMapping("/genre/{genreName}")
    public List<Movie> getMoviesByGenre(@PathVariable String genreName) {

        return movieRepository.findByGenresContainingIgnoreCase(genreName);
    }


    @PostMapping("/import")
    public String importMovies() {
        int[] genreIds = {
                28, 12, 16, 35, 80, 99, 18, 10751, 14, 36, 27, 10402, 9648, 10749, 878, 10770, 53, 10752, 37, 10759
        };
        for (int id : genreIds) {
            movieApiService.fetchAndSaveMoviesByCategory(id);
        }
        return "Filmler başarıyla yüklendi!";
    }
    @GetMapping("/popular")
    public List<Movie> getPopularMovies() {
        return movieRepository.findTop10ByOrderByRatingDesc(); // ilk 10 yüksek puanlı
    }

}