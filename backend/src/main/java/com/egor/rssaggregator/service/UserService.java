package com.egor.rssaggregator.service;

import com.egor.rssaggregator.dto.input.LoginDto;
import com.egor.rssaggregator.dto.input.RegDto;
import com.egor.rssaggregator.dto.output.TokenDto;
import com.egor.rssaggregator.exception.DuplicateUser;
import com.egor.rssaggregator.exception.IncorrectPassword;
import com.egor.rssaggregator.exception.UserNotFound;

public interface UserService {
    TokenDto login(LoginDto loginDto) throws UserNotFound, IncorrectPassword;

    void reg(RegDto regDto) throws DuplicateUser;
}
