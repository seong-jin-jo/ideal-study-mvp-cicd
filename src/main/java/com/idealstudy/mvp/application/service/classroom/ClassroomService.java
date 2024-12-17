package com.idealstudy.mvp.application.service.classroom;

import com.idealstudy.mvp.application.dto.classroom.ClassroomPageResultDto;
import com.idealstudy.mvp.application.component.ClassroomComponent;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.application.repository.ClassroomRepository;
import com.idealstudy.mvp.application.repository.LikedRepository;
import com.idealstudy.mvp.presentation.dto.classroom.ClassroomRequestDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;

import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    private final LikedRepository likedRepository;

    private final ClassroomComponent classroomComponent;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository,
                            @Qualifier("likedClassroomRepositoryImpl") LikedRepository likedRepository,
                            ClassroomComponent classroomComponent) {
        this.classroomRepository = classroomRepository;
        this.likedRepository = likedRepository;
        this.classroomComponent = classroomComponent;
    }

    public ClassroomResponseDto createClassroom(ClassroomRequestDto request) {

        return TryCatchServiceTemplate.execute(() -> classroomRepository.save(request.getTitle(),
                request.getDescription(), request.getCapacity(), request.getThumbnail()), null,
                DBErrorMsg.CREATE_ERROR);
    }

    public ClassroomPageResultDto getAllClassrooms() {

        return TryCatchServiceTemplate.execute(() -> classroomRepository.findAll(),
                null, DBErrorMsg.SELECT_ERROR);
    }

    public ClassroomResponseDto getClassroomById(String id) {

        return TryCatchServiceTemplate.execute(() -> classroomRepository.findById(id),
                null, DBErrorMsg.SELECT_ERROR);
    }

    public ClassroomResponseDto updateClassroom(String id, ClassroomRequestDto request, String teacherId) {

        return TryCatchServiceTemplate.execute(() -> classroomRepository.update(id,request.getTitle(),
                        request.getDescription(), request.getCapacity(), request.getThumbnail()),
                () -> classroomComponent.checkMyClassroom(teacherId, id), DBErrorMsg.UPDATE_ERROR);
    }

    public void deleteClassroom(String classroomId, String teacherId) {

        TryCatchServiceTemplate.execute(() -> {
            classroomRepository.deleteById(classroomId);
            return null;
        },
        () -> classroomComponent.checkMyClassroom(teacherId, classroomId), DBErrorMsg.DELETE_ERROR);
    }

    public void updateLiked() {


    }

    public int countLiked(Long classroomId) {

        String collection = "classrooms";
        return likedRepository.countById(classroomId);
    }
}
