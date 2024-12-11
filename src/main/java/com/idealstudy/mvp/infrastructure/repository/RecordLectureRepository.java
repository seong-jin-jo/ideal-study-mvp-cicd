package com.idealstudy.mvp.infrastructure.repository;

import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLectureDto;

import java.io.File;

public interface RecordLectureRepository {

    RecordLectureDto create(String classroomId, String title, String description, File file, int order);

    void getDetail(Long id);

    void selectList(String classroomId);

    /**
     * 파라미터에 null값을 넣으면, 해당 파라미터는 변경 대상에서 제외됨.
     */
    void updateVideo(Long id, String title, String description, String videoUrl);

    void updateOrder(Long id, int order);

    void updatePlaytime(Long id, int playtime);

    void delete(Long id, String videoEndPoint);
}
