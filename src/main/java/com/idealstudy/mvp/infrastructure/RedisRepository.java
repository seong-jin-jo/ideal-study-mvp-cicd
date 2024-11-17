package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.infrastructure.dto.EmailTokenDto;

public interface RedisRepository {

    void addToken(String email, String token);

    EmailTokenDto getToken(String email);
}
