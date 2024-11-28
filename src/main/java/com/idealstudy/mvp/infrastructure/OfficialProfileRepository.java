package com.idealstudy.mvp.infrastructure;


import com.idealstudy.mvp.application.dto.OfficialProfileDto;

public interface OfficialProfileRepository {

    void create();

    OfficialProfileDto findById(Long id);
}
