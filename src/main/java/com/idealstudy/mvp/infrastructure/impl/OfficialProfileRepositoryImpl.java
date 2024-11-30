package com.idealstudy.mvp.infrastructure.impl;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.infrastructure.OfficialProfileRepository;
import com.idealstudy.mvp.infrastructure.jpa.entity.OfficialProfileEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.TeacherEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.OfficialProfileJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OfficialProfileRepositoryImpl implements OfficialProfileRepository {

    @Autowired
    private final OfficialProfileJpaRepository officialProfileJpaRepository;

    @Override
    public void create(String teacherId) {

        String initContent = "<p>최초 프로필 생성됨</p>";
        
        OfficialProfileEntity entity = OfficialProfileEntity.builder()
                .id(teacherId)
                .content(initContent)
                .build();

        officialProfileJpaRepository.save(entity);
    }

    @Override
    public OfficialProfileDto findByTeacherId(String teacherId) {

        OfficialProfileEntity entity = officialProfileJpaRepository.findById(teacherId).orElseThrow();

        return entity.toDto();
    }

    @Override
    public void update(OfficialProfileDto dto) {

        officialProfileJpaRepository.save(OfficialProfileEntity.toEntity(dto));
    }
}
