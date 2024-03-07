package com.egor.rssaggregator.service;

import com.egor.rssaggregator.dto.input.LoginDto;
import com.egor.rssaggregator.dto.input.RegDto;
import com.egor.rssaggregator.dto.output.TokenDto;

public interface UserService {
    TokenDto login(LoginDto loginDto);

    void reg(RegDto regDto);
}
