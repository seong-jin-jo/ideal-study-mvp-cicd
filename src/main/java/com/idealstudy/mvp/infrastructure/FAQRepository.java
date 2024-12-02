package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.classroom.FAQDto;
import com.idealstudy.mvp.application.dto.classroom.FAQPageResultDto;

public interface FAQRepository {

    void create(FAQDto dto);

    FAQDto findById(Long faqId);

    FAQPageResultDto findList(PageRequestDto dto);

    FAQDto update(FAQDto dto);

    void delete(Long faqId);
}
