package org.hse.software.construction.authservice.Service.Impl;

import jakarta.validation.*;
import org.hse.software.construction.authservice.Service.JwtService;
import org.hse.software.construction.authservice.Service.UserService;
import org.hse.software.construction.authservice.Utility.AuthenticationRequest;
import org.hse.software.construction.authservice.Model.Role;
import org.hse.software.construction.authservice.Model.User;
import org.hse.software.construction.authservice.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional
    public HttpEntity<String> registerUser(User user) {
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        if (!Pattern.matches(emailPattern, user.getEmail())) {
            throw new RuntimeException("Email is not valid");
        }

        if (!Pattern.matches(passwordPattern, user.getPassword())) {
            throw new RuntimeException("Password must be at least 8 characters long and include letters of both cases, digits, and special characters");
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("User already exists");
        }

        if (user.getUsername() == null) {
            throw new RuntimeException("username cant be empty");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        log.info("saved new user");
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @Override
    @Transactional
    public ResponseEntity<String> login(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.email);
        if (user == null) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(token);
    }

    @Override
    @Transactional
    public ResponseEntity<Claims> getUserInfoFromToken(String token) {
        String email = jwtService.getUsernameFromToken(token);
        User user = userRepository.findByEmail(email);
        if (!jwtService.validateToken(token, user)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Claims claims = jwtService.getAllClaimsFromToken(token);
        return ResponseEntity.ok(claims);
    }
}
