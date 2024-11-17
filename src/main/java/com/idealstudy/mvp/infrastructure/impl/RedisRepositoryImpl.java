package com.idealstudy.mvp.infrastructure.impl;

import com.idealstudy.mvp.infrastructure.RedisRepository;
import com.idealstudy.mvp.infrastructure.dto.EmailTokenDto;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
    @Override
    public void addToken(String email, String token) {

    }

    @Override
    public EmailTokenDto getToken(String email) {
        return null;
    }
}
