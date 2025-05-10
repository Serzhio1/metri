package com.serzhio_pet_projects.metri.controller;


import com.serzhio_pet_projects.metri.dto.AuthRequestDto;
import com.serzhio_pet_projects.metri.dto.AuthResponseDto;
import com.serzhio_pet_projects.metri.model.UserModel;
import com.serzhio_pet_projects.metri.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody UserModel user) {
        AuthResponseDto response = authService.register(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
