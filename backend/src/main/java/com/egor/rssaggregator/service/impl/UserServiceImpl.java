package com.egor.rssaggregator.service.impl;

import com.egor.rssaggregator.dto.output.user.TokenDto;
import com.egor.rssaggregator.entity.User;
import com.egor.rssaggregator.exception.DuplicateUserException;
import com.egor.rssaggregator.exception.IncorrectPasswordException;
import com.egor.rssaggregator.exception.UserNotFoundException;
import com.egor.rssaggregator.repo.UserRepo;
import com.egor.rssaggregator.security.JwtUtilities;
import com.egor.rssaggregator.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtUtilities jwtUtilities;
    private final UserRepo userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public TokenDto login(User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        User user1 = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found."));

        if (!passwordEncoder.matches(password, user1.getPassword())) {
            throw new IncorrectPasswordException("Incorrect password!");
        }

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(jwtUtilities.generateToken(user1.getUsername(), "ROLE_USER"));
        return tokenDto;
    }

    @Override
    public void reg(User user) {
        boolean emailIsExist =
                userRepository.existsByEmail(user.getEmail());

        if (emailIsExist) {
            throw new DuplicateUserException("Duplicate E-Mail.");
        }
        userRepository.save(user);
    }
}
