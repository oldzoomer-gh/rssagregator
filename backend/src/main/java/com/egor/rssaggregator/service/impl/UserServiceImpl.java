package com.egor.rssaggregator.service.impl;

import com.egor.rssaggregator.dto.input.LoginDto;
import com.egor.rssaggregator.dto.input.RegDto;
import com.egor.rssaggregator.dto.output.TokenDto;
import com.egor.rssaggregator.entity.User;
import com.egor.rssaggregator.exception.DuplicateUser;
import com.egor.rssaggregator.exception.IncorrectPassword;
import com.egor.rssaggregator.exception.UserNotFound;
import com.egor.rssaggregator.mapper.input.RegDataInputMapper;
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
    private final RegDataInputMapper registrationDataInputMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public TokenDto login(LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFound("User not found."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectPassword("Incorrect password!");
        }

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(jwtUtilities.generateToken(user.getUsername(), "ROLE_USER"));
        return tokenDto;
    }

    @Override
    public void reg(RegDto regDto) {
        boolean emailIsExist =
                userRepository.existsByEmail(regDto.getEmail());

        if (emailIsExist) {
            throw new DuplicateUser("Duplicate E-Mail.");
        }

        User user = registrationDataInputMapper.toEntity(regDto);
        userRepository.save(user);
    }
}
