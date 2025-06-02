package com.cinematch.repository;

import com.cinematch.entity.WatchedMovie;
import com.cinematch.entity.Movie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchedMovieRepository extends JpaRepository<WatchedMovie, Long> {
    List<WatchedMovie> findByUserIdAndLiked(Long userId, boolean liked);
}
