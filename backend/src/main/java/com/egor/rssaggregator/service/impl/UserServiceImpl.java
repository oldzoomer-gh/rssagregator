package com.egor.rssaggregator.service.impl;

import com.egor.rssaggregator.dto.TokenDto;
import com.egor.rssaggregator.dto.UserDto;
import com.egor.rssaggregator.entity.User;
import com.egor.rssaggregator.exception.DuplicateUserException;
import com.egor.rssaggregator.exception.IncorrectPasswordException;
import com.egor.rssaggregator.exception.UserNotFoundException;
import com.egor.rssaggregator.mapper.UserMapper;
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
    private final UserMapper registrationDataInputMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public TokenDto login(UserDto userDto) {
        String email = userDto.getEmail();
        String password = userDto.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectPasswordException("Incorrect password!");
        }

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(jwtUtilities.generateToken(user.getUsername(), "ROLE_USER"));
        return tokenDto;
    }

    @Override
    public void reg(UserDto userDto) {
        boolean emailIsExist =
                userRepository.existsByEmail(userDto.getEmail());

        if (emailIsExist) {
            throw new DuplicateUserException("Duplicate E-Mail.");
        }

        User user = registrationDataInputMapper.toEntity(userDto);
        userRepository.save(user);
    }
}
