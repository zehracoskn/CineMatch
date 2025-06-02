package com.cinematch.repository;

import com.cinematch.entity.WatchlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface WatchlistRepository extends JpaRepository<WatchlistItem, Long> {
    Optional<WatchlistItem> findById(Long id);     // mevcut kullanımın bozulmaz
    List<WatchlistItem> findByUserId(Long userId); // yeni kullanım eklenir
}
