package ru.gavrilovegor519.rssaggregator.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.gavrilovegor519.rssaggregator.entity.User;
import ru.gavrilovegor519.rssaggregator.exception.DuplicateUserException;
import ru.gavrilovegor519.rssaggregator.exception.IncorrectPasswordException;
import ru.gavrilovegor519.rssaggregator.exception.UserNotFoundException;
import ru.gavrilovegor519.rssaggregator.repo.UserRepo;
import ru.gavrilovegor519.rssaggregator.security.JwtUtilities;

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
    void loginWithExistUser() throws UserNotFoundException, IncorrectPasswordException {
        var userDto = new User();
        userDto.setEmail("1@1.ru");
        userDto.setPassword("test");

        var user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        when(userRepository.findByEmail("1@1.ru")).thenReturn(Optional.of(user));

        userService.login(userDto);
    }

    @Test
    void loginWithExistUserButWithIncorrectPassword() {
        var userDto = new User();
        userDto.setEmail("1@1.ru");
        userDto.setPassword("test");

        var user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode("test2"));

        when(userRepository.findByEmail("1@1.ru")).thenReturn(Optional.of(user));

        assertThrows(IncorrectPasswordException.class, () -> userService.login(userDto));
    }

    @Test
    void loginWithNotExistUser() {
        var userDto = new User();
        userDto.setEmail("1@1.ru");

        assertThrows(UserNotFoundException.class, () -> userService.login(userDto));
    }

    @Test
    void registrationWithDuplicatedUser() {
        var userData = new User();
        userData.setEmail("1@1.ru");

        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(DuplicateUserException.class, () -> userService.reg(userData));
    }
}