package com.egor.rssaggregator.service.impl;

import com.egor.rssaggregator.dto.input.LoginDto;
import com.egor.rssaggregator.dto.input.RegDto;
import com.egor.rssaggregator.entity.User;
import com.egor.rssaggregator.exception.DuplicateUser;
import com.egor.rssaggregator.exception.IncorrectPassword;
import com.egor.rssaggregator.exception.UserNotFound;
import com.egor.rssaggregator.repo.UserRepo;
import com.egor.rssaggregator.security.JwtUtilities;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private JwtUtilities jwtUtilities;

    @Mock
    private UserRepo userRepository;

    @Spy
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void loginWithExistUser() throws UserNotFound, IncorrectPassword {
        var loginDTO = LoginDto.builder()
                .email("1@1.ru")
                .password("test")
                .build();

        var user = User.builder()
                .email(loginDTO.getEmail())
                .password(passwordEncoder.encode(loginDTO.getPassword()))
                .build();

        when(userRepository.findByEmail("1@1.ru")).thenReturn(Optional.of(user));

        userService.login(loginDTO);
    }

    @Test
    void loginWithExistUserButWithIncorrectPassword() {
        var loginDTO = LoginDto.builder()
                .email("1@1.ru")
                .password("test")
                .build();

        var user = User.builder()
                .email(loginDTO.getEmail())
                .password(passwordEncoder.encode("test2"))
                .build();

        when(userRepository.findByEmail("1@1.ru")).thenReturn(Optional.of(user));

        assertThrows(IncorrectPassword.class, () -> userService.login(loginDTO));
    }

    @Test
    void loginWithNotExistUser() {
        var loginDTO = LoginDto.builder()
                .email("test1")
                .password("test")
                .build();

        assertThrows(UserNotFound.class, () -> userService.login(loginDTO));
    }

    @Test
    void registrationWithDuplicatedUser() {
        var userData = RegDto.builder()
                .email("1@1.ru")
                .build();

        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(DuplicateUser.class, () -> userService.reg(userData));
    }
}