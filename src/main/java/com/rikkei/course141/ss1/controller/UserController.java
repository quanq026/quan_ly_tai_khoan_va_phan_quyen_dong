package com.rikkei.course141.ss1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import com.rikkei.course141.ss1.dto.response.ApiResponse;
import com.rikkei.course141.ss1.model.User;
import com.rikkei.course141.ss1.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    public UserController(UserRepository userRepository) { this.userRepository = userRepository; }

    @GetMapping("/me") public ResponseEntity<ApiResponse<User>> me(Authentication auth) {
        return ResponseEntity.ok(ApiResponse.success(userRepository.findByEmail(auth.getName()).orElseThrow()));
    }

    @PutMapping("/{id}/role") public ResponseEntity<ApiResponse<User>> updateRole(@PathVariable Long id, @RequestBody Map<String, String> body) {
        User user = userRepository.findById(id).orElseThrow();
        user.setRole(body.get("role"));
        return ResponseEntity.ok(ApiResponse.success(userRepository.save(user)));
    }
}
