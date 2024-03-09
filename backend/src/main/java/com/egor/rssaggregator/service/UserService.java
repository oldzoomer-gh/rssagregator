package com.egor.rssaggregator.service;

import com.egor.rssaggregator.dto.input.LoginDto;
import com.egor.rssaggregator.dto.input.RegDto;
import com.egor.rssaggregator.dto.output.TokenDto;

/**
 * User management service layer. User can login and register.
 * @author Egor Gavrilov (gavrilovegor519@gmail.com)
 */
public interface UserService {

    /**
     * Login user and return JWT Bearer token.
     * @param loginDto Login data
     * @return JWT Bearer token
     */
    TokenDto login(LoginDto loginDto);

    /**
     * Register new user.
     * @param regDto Registration data
     */
    void reg(RegDto regDto);
}
