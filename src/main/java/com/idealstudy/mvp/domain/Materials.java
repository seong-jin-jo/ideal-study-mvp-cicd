package com.idealstudy.mvp.domain;

import com.idealstudy.mvp.application.dto.classroom.inclass.MaterialsDto;
import com.idealstudy.mvp.enums.classroom.MaterialsStatus;
import com.idealstudy.mvp.enums.error.SecurityErrorMsg;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.application.repository.ClassroomRepository;
import com.idealstudy.mvp.application.repository.inclass.EnrollmentRepository;
import com.idealstudy.mvp.application.repository.inclass.MaterialsRepository;

import java.io.*;
import java.util.UUID;

public class Materials {

    private final MaterialsRepository materialsRepository;

    private final EnrollmentRepository enrollmentRepository;

    private final ClassroomRepository classroomRepository;

    private final String UPLOAD_PATH;

    public Materials(MaterialsRepository materialsRepository, EnrollmentRepository enrollmentRepository,
                     ClassroomRepository classroomRepository,
                     String uploadPath) {
        this.materialsRepository = materialsRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.classroomRepository = classroomRepository;
        UPLOAD_PATH = uploadPath;
    }

    public String saveFile(InputStream inputStream, String originalFileName) throws IOException {

        /*
            모든 파일명을 랜덤하게 한 뒤, 해당 파일명의 실제 이름을 따로 DB에 일대일 매핑해두는 식으로 저장하면
            파일 원본명을 지킴과 동시에 보안적으로도 안전함.
         */

        String uuid = UUID.randomUUID().toString();
        String filePath = UPLOAD_PATH + uuid + "_" + originalFileName;

        try (OutputStream outputStream = new FileOutputStream(filePath)) {

            byte[] buffer = new byte[8192]; // 8KB 버퍼 크기 설정

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다.");
        }

        return filePath;
    }

    public File getFile(String materialUri, Long id, String userId, Role role) throws RuntimeException {

        MaterialsDto dto = materialsRepository.getDetail(id);

        if(role == Role.ROLE_STUDENT) {
            validateStudent(dto.getClassroomId(), userId, dto.getStatus(), dto.getStudentId());
        } else if(role == Role.ROLE_TEACHER) {
            validateTeacher(userId, dto.getClassroomId());
        } else {
            throw new SecurityException(SecurityErrorMsg.ROLE_EXCEPTION.toString());
        }

        File file = new File(materialUri);
        if (!file.exists()) {
            throw new RuntimeException("파일이 존재하지 않습니다.");
        }
        return file;
    }

    private void validateStudent(String classroomId, String studentId, MaterialsStatus status,
                                 String registeredStudentId)
            throws SecurityException {

        if(status == MaterialsStatus.PUBLIC) {
            if( !enrollmentRepository.checkAffiliated(classroomId, studentId))
                throw new SecurityException(SecurityErrorMsg.NOT_AFFILIATED.toString());
        }

        if(status == MaterialsStatus.INDIVIDUAL) {
            if( !studentId.equals(registeredStudentId))
                throw new SecurityException(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
        }
    }

    private void validateTeacher(String teacherId, String classroomId)
            throws SecurityException{

        String registeredTeacherId = classroomRepository.findById(classroomId).getCreatedBy();
        if( !registeredTeacherId.equals(teacherId))
            throw new SecurityException(SecurityErrorMsg.NOT_YOURS.toString());
    }
}
