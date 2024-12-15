package com.idealstudy.mvp.application.service.classroom.inclass;

import com.idealstudy.mvp.application.dto.classroom.inclass.LiveLectureDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.LiveLecturePageResultDto;
import com.idealstudy.mvp.enums.classroom.Platform;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.infrastructure.repository.inclass.LiveLectureRepository;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class LiveLectureService {

    @Autowired
    private final LiveLectureRepository liveLectureRepository;


    public LiveLectureDto create(String classroomId, String title, LocalDateTime startTime, LocalDateTime endTime,
                                 String studySpace, Platform flatform) {

        return TryCatchServiceTemplate.execute(()-> liveLectureRepository.create(classroomId, title,
                        startTime, endTime, studySpace, flatform), null,
                DBErrorMsg.CREATE_ERROR);
    }

    public LiveLectureDto getOne(Long id) {

        return TryCatchServiceTemplate.execute(() -> liveLectureRepository.getOne(id),
                null, DBErrorMsg.SELECT_ERROR);
    }

    public LiveLecturePageResultDto getList(String classroomId) {

        return TryCatchServiceTemplate.execute(() -> liveLectureRepository.getList(classroomId),
                null, DBErrorMsg.SELECT_ERROR);
    }

    public LiveLectureDto update(Long id, String title, LocalDateTime startTime, LocalDateTime endTime,
                                 String studySpace, Platform flatform) {

        return TryCatchServiceTemplate.execute(() -> liveLectureRepository.update(id, title, startTime, endTime,
                        studySpace, flatform), null, DBErrorMsg.UPDATE_ERROR);
    }

    public void delete(Long id) {

        TryCatchServiceTemplate.execute(() -> {
            liveLectureRepository.delete(id);
            return null;
        }, null, DBErrorMsg.DELETE_ERROR);
    }

}
