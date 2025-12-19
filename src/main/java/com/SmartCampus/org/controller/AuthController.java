package com.SmartCampus.org.controller;

import com.SmartCampus.org.Entity.User;
import com.SmartCampus.org.dto.LoginRequest;
import com.SmartCampus.org.repositories.UserRepository;
import com.SmartCampus.org.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder; // Import this!
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    // We inject the encoder defined in SecurityConfig (even if it is NoOp for now)
    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- 1. REGISTER ENDPOINT ---
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Error: Email is already in use!"));
        }

        // Create new user's account
        // Important: We encode the password (even if NoOp, this keeps code clean for future)
        user.setUsername(user.getEmail()); // Add this line if your User entity has a username field
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
    }

    // --- 2. LOGIN ENDPOINT ---
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // A. Authenticate (Checks email/password)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        // B. Fetch User to get Role
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();

        // C. Generate Token
        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().toString());

        // D. Return Token
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.getRole().toString());

        return ResponseEntity.ok(response);
    }
}