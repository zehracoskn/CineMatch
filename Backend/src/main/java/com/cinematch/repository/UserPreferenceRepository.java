// src/main/java/com/example/demo/repository/UserPreferenceRepository.java
package com.cinematch.repository;

import com.cinematch.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {
    Optional<UserPreference> findById(Long id);
}