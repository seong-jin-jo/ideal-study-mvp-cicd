package com.idealstudy.mvp.application.service.classroom.inclass;

import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLectureDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLecturePageResultDto;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.infrastructure.repository.inclass.RecordLectureRepository;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
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
    private final RecordLectureRepository recordLectureRepository;

    public RecordLectureDto create(String classroomId, String title, String description, String videoEndPoint, Integer order) {

        return TryCatchServiceTemplate.execute(() ->
                recordLectureRepository.create(classroomId, title, description, videoEndPoint, order),
                null, DBErrorMsg.CREATE_ERROR);
    }

    public RecordLectureDto getDetail(Long id) {

        return TryCatchServiceTemplate.execute(() -> recordLectureRepository.getDetail(id),
                null, DBErrorMsg.SELECT_ERROR);
    }

    public RecordLecturePageResultDto selectList(String classroomId) {

        return TryCatchServiceTemplate.execute(() -> recordLectureRepository.selectList(classroomId),
                null, DBErrorMsg.SELECT_ERROR);
    }

    public RecordLectureDto update(Long id, String title, String description, String videoEndPoint, Integer order) {

        return TryCatchServiceTemplate.execute(() -> recordLectureRepository.update(id, title, description,
                        videoEndPoint, order),null, DBErrorMsg.UPDATE_ERROR);
    }

    public void delete(Long id) {

        TryCatchServiceTemplate.execute(() -> {
            recordLectureRepository.delete(id);
            return null;
        },null, DBErrorMsg.DELETE_ERROR);
    }
}
