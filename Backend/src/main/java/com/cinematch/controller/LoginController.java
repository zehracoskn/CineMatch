package com.cinematch.controller;

import com.cinematch.dto.LoginRequest;
import com.cinematch.entity.User;
import com.cinematch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        Map<String, String> response = new HashMap<>();

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        // Unified error message for both cases: user not found or wrong password
        if (optionalUser.isEmpty() ||
                !optionalUser.get().getPassword().equals(request.getPassword())) {
            response.put("status", "error");
            response.put("message", "Incorrect password or email");
        } else {
            response.put("status", "ok");
            response.put("message", "Login successful");
        }

        return response;
    }
}
