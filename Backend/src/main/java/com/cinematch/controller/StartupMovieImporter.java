package com.cinematch.init;

import com.cinematch.service.MovieApiService;
import com.cinematch.repository.MovieRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartupMovieImporter {

    @Autowired
    private MovieApiService movieApiService;

    @Autowired
    private MovieRepository movieRepository;

    @PostConstruct
    public void init() {
        if (movieRepository.count() > 0) {
            System.out.println("🎬 Movies already imported, skipping.");
            return;
        }

        System.out.println("📥 Importing movies at startup...");

        int[] genreIds = {
                28, 12, 16, 35, 80, 99, 18, 10751,
                14, 36, 27, 10402, 9648, 10749,
                878, 10770, 53, 10752, 37, 10759
        };

        for (int id : genreIds) {
            movieApiService.fetchAndSaveMoviesByCategory(id);
        }

        System.out.println("✅ Movie import completed at startup.");
    }
}
