package com.idealstudy.mvp.infrastructure.impl.classroom;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.FAQDto;
import com.idealstudy.mvp.application.dto.classroom.FAQPageResultDto;
import com.idealstudy.mvp.infrastructure.FAQRepository;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.FAQEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.FAQJpaRepository;
import com.idealstudy.mvp.mapstruct.FAQMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class FAQRepositoryImpl implements FAQRepository {

    @Autowired
    private final FAQJpaRepository faqJpaRepository;

    @Autowired
    private final FAQMapper faqMapper;

    @Override
    public void create(FAQDto dto) {

        FAQEntity entity = faqMapper.dtoTOEntity(dto);
        faqJpaRepository.save(entity);
    }

    @Override
    public FAQDto findById(Long faqId) {

        FAQEntity entity = faqJpaRepository.findById(faqId).orElseThrow();

        return faqMapper.entityToDto(entity);
    }

    @Override
    public FAQPageResultDto findList(PageRequestDto dto) {

        Pageable pageable = dto.getPageable(Sort.by("regDate").ascending());

        Page<FAQEntity> results = faqJpaRepository.findAll(pageable);

        Function<FAQEntity, FAQDto> fn = (faqMapper::entityToDto);

        return faqMapper.toFAQPageResultDto(new PageResultDto<FAQDto, FAQEntity>(results, fn));
    }

    @Override
    public FAQDto update(FAQDto dto) {

        FAQEntity entity = faqJpaRepository.findById(dto.getId()).orElseThrow();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());

        FAQEntity result = faqJpaRepository.save(entity);
        return faqMapper.entityToDto(result);
    }

    @Override
    public void delete(Long faqId) {

        FAQEntity entity = faqJpaRepository.findById(faqId).orElseThrow();
        faqJpaRepository.delete(entity);
    }
}
