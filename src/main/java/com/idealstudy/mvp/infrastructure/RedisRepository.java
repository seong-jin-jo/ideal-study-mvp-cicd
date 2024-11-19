package com.idealstudy.mvp.infrastructure;

public interface RedisRepository {

    void addToken(String email, String token);

    String getToken(String email);

    Boolean deleteToken(String email);
}
