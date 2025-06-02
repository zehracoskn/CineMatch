package com.cinematch.repository;
import java.util.Optional;
import com.cinematch.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitleAndReleaseYear(String title, Integer releaseYear); // for same movie check
    List<Movie> findByGenresContainingIgnoreCase(String genre);

    List<Movie> findTop10ByOrderByRatingDesc();

}
