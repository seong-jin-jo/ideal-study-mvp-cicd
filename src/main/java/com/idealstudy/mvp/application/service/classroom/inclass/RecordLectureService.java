package com.idealstudy.mvp.application.service.classroom.inclass;

import com.idealstudy.mvp.infrastructure.repository.RecordLectureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RecordLectureService {

    @Autowired
    private final RecordLectureRepository lectureRepository;

    public void create() {

    }

    public void getDetail() {

    }

    public void selectList() {

    }

    public void update() {

    }

    public void delete() {

    }
}
