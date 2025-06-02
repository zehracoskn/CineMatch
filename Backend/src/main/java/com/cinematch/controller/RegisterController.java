package com.cinematch.controller;
import com.cinematch.dto.RegisterRequest;
import com.cinematch.entity.User;
import com.cinematch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody RegisterRequest request) {
        Map<String, String> response = new HashMap<>();

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            response.put("status", "error");
            response.put("message", "Email already registered");
            return response;
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        if (request.getPreferredGenres() != null) {
            user.setPreferredGenres(request.getPreferredGenres());
        }
        if (request.getPreferredActors() != null) {
            user.setPreferredActors(request.getPreferredActors());
        }
        if (request.getPreferredDirectors() != null) {
            user.setPreferredDirectors(request.getPreferredDirectors());
        }

        userRepository.save(user);

        response.put("status", "ok");
        response.put("message", "Registration successful");
        return response;
    }
}
