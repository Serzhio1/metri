package com.serzhio_pet_projects.metri.service;

import com.serzhio_pet_projects.metri.dto.AuthRequestDto;
import com.serzhio_pet_projects.metri.dto.AuthResponseDto;
import com.serzhio_pet_projects.metri.enums.Role;
import com.serzhio_pet_projects.metri.model.UserModel;
import com.serzhio_pet_projects.metri.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDto register(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return new AuthResponseDto(jwtService.generateToken(user));
    }

    public AuthResponseDto authenticate(AuthRequestDto authRequestDto) {
        UserModel user = userRepository.findByEmail(authRequestDto.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(authRequestDto.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return new AuthResponseDto(jwtService.generateToken(user));
    }
}
