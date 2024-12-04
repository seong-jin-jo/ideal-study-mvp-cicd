package com.idealstudy.mvp.application.service.classroom.preclass;

import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryPageResultDto;
import com.idealstudy.mvp.enums.DBErrorMsg;
import com.idealstudy.mvp.enums.SecurityErrorMsg;
import com.idealstudy.mvp.enums.classroom.Visibility;
import com.idealstudy.mvp.infrastructure.ClassInquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassInquiryService {

    @Autowired
    private final ClassInquiryRepository classInquiryRepository;

    public void create(String title, String content, String classroomId, String writer, Visibility visibility)
            throws IOException {

        try {
            classInquiryRepository.create(title, content, classroomId, writer, visibility);
        } catch (Exception e) {
            log.error(DBErrorMsg.CREATE_ERROR.toString());
            throw new IOException(DBErrorMsg.CREATE_ERROR.toString());
        }
    }

    public ClassInquiryDto findById(Long classInquiryId, String userId) {

        ClassInquiryDto dto = classInquiryRepository.findById(classInquiryId);
        if(dto.getVisibility().equals(Visibility.PRIVATE)) {

            if( !checkMine(classInquiryId, userId)) {
                log.error(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
                throw new SecurityException(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
            }
        }
        return dto;
    }

    public ClassInquiryPageResultDto findList(String classId, int page) throws IOException {

        ClassInquiryPageResultDto dto = null;
        try {
            dto = classInquiryRepository.findList(classId, page);
            return dto;
        } catch (Exception e) {
            log.error(DBErrorMsg.SELECT_ERROR.toString());
            throw new IOException(DBErrorMsg.SELECT_ERROR.toString());
        }
    }


    public ClassInquiryDto update(Long classInquiryId, String title, String content, String classroomId, String writer,
                                  Visibility visibility) throws IOException {

        ClassInquiryDto dto = null;
        try {
            if( !checkMine(classInquiryId, writer))
                throw new SecurityException();

            dto = classInquiryRepository.update(classInquiryId, title, content, classroomId, writer, visibility);
            return dto;
        } catch (SecurityException e) {
            log.error(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
            throw new SecurityException(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
        } catch (Exception e) {
            log.error(DBErrorMsg.UPDATE_ERROR.toString());
            throw new IOException(DBErrorMsg.UPDATE_ERROR.toString());
        }
    }

    public void delete(Long inquiryId, String deleter) throws IOException {

        try{
            if( !checkMine(inquiryId, deleter))
                throw new SecurityException();

            classInquiryRepository.delete(inquiryId);
        } catch (SecurityException e) {
            log.error(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
            throw new SecurityException(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
        } catch (Exception e) {
            log.error(DBErrorMsg.DELETE_ERROR.toString());
            throw new IOException(DBErrorMsg.DELETE_ERROR.toString());
        }
    }

    private boolean checkMine(Long classInquiryId, String userId) {

        ClassInquiryDto dto = classInquiryRepository.findById(classInquiryId);

        if(dto.getCreatedBy().equals(userId))
            return true;

        return false;
    }
}
