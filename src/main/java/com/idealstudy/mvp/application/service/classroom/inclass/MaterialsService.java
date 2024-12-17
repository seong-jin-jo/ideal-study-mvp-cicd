package com.idealstudy.mvp.application.service.classroom.inclass;

import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.MaterialsDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.MaterialsPageResultDto;
import com.idealstudy.mvp.domain.Materials;
import com.idealstudy.mvp.enums.classroom.MaterialsStatus;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.enums.error.SecurityErrorMsg;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.infrastructure.repository.ClassroomRepository;
import com.idealstudy.mvp.infrastructure.repository.inclass.EnrollmentRepository;
import com.idealstudy.mvp.infrastructure.repository.inclass.MaterialsRepository;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class MaterialsService {

    private final MaterialsRepository materialsRepository;

    // 자신의 클래스인지 체크하는 용도로 사용
    private final ClassroomRepository classroomRepository;

    // 해당 클래스에 소속되어 있는지 체크하는 용도로 사용
    private final EnrollmentRepository enrollmentRepository;

    private final Materials materials;

    @Autowired
    public MaterialsService(MaterialsRepository materialsRepository, ClassroomRepository classroomRepository, EnrollmentRepository enrollmentRepository,
                            @Value("${upload.path}") String uploadPath) {
        this.materialsRepository = materialsRepository;
        this.classroomRepository = classroomRepository;
        this.enrollmentRepository = enrollmentRepository;

        materials = new Materials(materialsRepository, enrollmentRepository
                , classroomRepository, uploadPath);
    }

    public MaterialsDto uploadPublic(String teacherId, String classroomId, String description, MultipartFile file,
                                     Integer orderNum, String title) {

        String studentId = null;
        MaterialsStatus status = MaterialsStatus.PUBLIC;

        return TryCatchServiceTemplate.execute(() -> tryCatchUpload(classroomId, studentId, orderNum,
                status, description, file, title),
                () -> checkMyClassroom(classroomId, teacherId), DBErrorMsg.CREATE_ERROR);
    }

    public MaterialsDto uploadIndividual(String teacherId, String classroomId, String studentId, String description,
                                         MultipartFile file, Integer orderNum, String title) {

        MaterialsStatus status = MaterialsStatus.INDIVIDUAL;

        return TryCatchServiceTemplate.execute(() -> tryCatchUpload(classroomId, studentId, orderNum, status,
                        description, file, title),
                () -> checkMyClassroom(classroomId, teacherId), DBErrorMsg.CREATE_ERROR);
    }

    public MaterialsDto getDetailForTeacher(Long id, String teacherId){

        return TryCatchServiceTemplate.execute(() -> {
            MaterialsDto dto = materialsRepository.getDetail(id);
            String classroomId = dto.getClassroomId();

            checkMyClassroom(classroomId, teacherId);

            return dto;
        },
        null, DBErrorMsg.SELECT_ERROR);
    }

    public MaterialsDto getDetailForStudent(Long id, String studentId){


        // 조회하는 유저가 해당 자료가 등록된 클래스에 속한 유저인지 확인해야 한다.
        // << PUBLIC인 경우 해당 사용자가 클래스에 속해있는 지만 비교ㅕ하면 된다.
        // 만일 INDIVIDUAL한 데이터인 경우, 해당 학생인지 정확히 판단해야 한다.
        // << dto 가져와서 INDIVIDUAL인지 확인하면 studentId 서로 비교하면 됨
        return TryCatchServiceTemplate.execute(() -> {
                    MaterialsDto dto = materialsRepository.getDetail(id);
                    String classroomId = dto.getClassroomId();

                    if(dto.getStatus() == MaterialsStatus.PUBLIC) {
                        if( !enrollmentRepository.belongToClassroom(classroomId, studentId))
                            throw new SecurityException(SecurityErrorMsg.NOT_AFFILIATED.toString());
                    } else if (dto.getStatus() == MaterialsStatus.INDIVIDUAL) {
                        if( !dto.getStudentId().equals(studentId))
                            throw new SecurityException(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
                    } else {
                        throw new IllegalArgumentException("잘못된 status 값입니다.");
                    }

                    return dto;
                },
                null, DBErrorMsg.SELECT_ERROR);
    }

    public MaterialsPageResultDto getListByClassroom(String classroomId, int page){

        return TryCatchServiceTemplate.execute(() -> materialsRepository.getListByClassroom(classroomId, page),
                null, DBErrorMsg.SELECT_ERROR);
    }

    public MaterialsPageResultDto getListForStudent(String classroomId, String studentId, int page){

        return TryCatchServiceTemplate.execute(() ->
                        materialsRepository.getListForStudent(classroomId, studentId, page),
                null, DBErrorMsg.SELECT_ERROR);
    }

    public MaterialsPageResultDto getListForTeacher(String classroomId, int page, String teacherId){

        return TryCatchServiceTemplate.execute(() -> materialsRepository.getListForTeacher(classroomId, page),
                () -> checkMyClassroom(classroomId, teacherId), DBErrorMsg.SELECT_ERROR);
    }

    public MaterialsDto update(Long id, String studentId, String description, MultipartFile multipartFile,
                               Integer orderNum, MaterialsStatus status, String classroomId, String teacherId,
                               String title){

        return TryCatchServiceTemplate.execute(() -> tryCatchUpdate(id, studentId, description, multipartFile,
                        orderNum, status, title),
                () -> checkMyClassroom(classroomId, teacherId), DBErrorMsg.UPDATE_ERROR);
    }

    public void delete(Long id, String classroomId, String teacherId){

        TryCatchServiceTemplate.execute(() -> {
            new File(materialsRepository.getDetail(id).getMaterialUri()).delete();

            materialsRepository.delete(id);
            return null;
        },
        () -> checkMyClassroom(classroomId, teacherId), DBErrorMsg.DELETE_ERROR);

    }

    public File getFile(String materialUri, Long id, String userId, Role role)
            throws RuntimeException {

        return materials.getFile(materialUri, id, userId, role);
    }

    /*
      본인 클래스룸 외의 자료에 대한 CRUD를 막기 위함.
      학생에 대한 조작은 컨트롤러에서 권한 상 이미 차단되므로 고려하지 않아도 됨.
     */
    private void checkMyClassroom(String classroomId, String teacherId) throws SecurityException{

        ClassroomResponseDto dto = classroomRepository.findById(classroomId);

        if( !dto.getCreatedBy().equals(teacherId))
            throw new SecurityException(SecurityErrorMsg.NOT_YOURS.toString());
    }

    private MaterialsDto tryCatchUpload(String classroomId, String studentId, Integer orderNum, MaterialsStatus status,
                                        String description, MultipartFile multipartFile, String title
    ) throws RuntimeException, IOException {

        String filePath = materials.saveFile(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
        try {
            return materialsRepository.upload(classroomId, studentId, orderNum, status, title, description,
                    filePath);
        } catch (Exception e) {
            File file = new File(filePath);
            file.delete();
            throw new RuntimeException(e);
        }
    }

    private MaterialsDto tryCatchUpdate(Long id, String studentId, String description, MultipartFile multipartFile,
                                        Integer orderNum, MaterialsStatus status, String title) {

        log.info("materialId = " + id);

        try {
            MaterialsDto dto = materialsRepository.getDetail(id);
            new File(dto.getMaterialUri()).delete();

            final String materialUri;
            if(multipartFile != null)
                materialUri = materials.saveFile(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
            else
                materialUri = null;

            return materialsRepository.update(id, studentId, orderNum, status,
                    description, materialUri, title);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
