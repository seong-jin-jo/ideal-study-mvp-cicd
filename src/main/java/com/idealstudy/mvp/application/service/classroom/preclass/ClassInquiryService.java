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
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ClassInquiryService {

    @Autowired
    private final ClassInquiryRepository classInquiryRepository;

    public void create(String title, String content, String classroomId, String writer, Visibility visibility) {

        try {
            classInquiryRepository.create(title, content, classroomId, writer, visibility);
        } catch (RuntimeException e) {
            log.error(DBErrorMsg.CREATE_ERROR.toString());
            throw new RuntimeException(DBErrorMsg.CREATE_ERROR.toString());
        }
    }

    public ClassInquiryDto findById(Long classInquiryId, String userId) {

        ClassInquiryDto dto = classInquiryRepository.findById(classInquiryId);
        if(dto.getVisibility().equals(Visibility.PRIVATE)) {

            if( userId == null || !checkMine(classInquiryId, userId)) {
                log.error(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
                throw new SecurityException(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
            }
        }
        return dto;
    }

    public ClassInquiryPageResultDto findListByClassId(String classId, int page) {

        ClassInquiryPageResultDto dto = null;
        try {
            dto = classInquiryRepository.findListByClassId(classId, page);
            return dto;
        } catch (RuntimeException e) {
            log.error(DBErrorMsg.SELECT_ERROR.toString());
            throw new RuntimeException(DBErrorMsg.SELECT_ERROR.toString());
        }
    }

    public ClassInquiryPageResultDto findListByMemberId(String userId, int page){
        ClassInquiryPageResultDto dto = null;
        try {
            dto = classInquiryRepository.findListByMemberId(userId, page);
            return dto;
        } catch (RuntimeException e) {
            log.error(DBErrorMsg.SELECT_ERROR.toString());
            throw new RuntimeException(DBErrorMsg.SELECT_ERROR.toString());
        }
    }


    public ClassInquiryDto update(Long classInquiryId, String title, String content, String classroomId, String writer,
                                  Visibility visibility) {

        ClassInquiryDto dto = null;
        try {
            if( !checkMine(classInquiryId, writer))
                throw new SecurityException();

            dto = classInquiryRepository.update(classInquiryId, title, content, classroomId, writer, visibility);
            return dto;
        } catch (SecurityException e) {
            log.error(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
            throw new SecurityException(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
        } catch (RuntimeException e) {
            log.error(DBErrorMsg.UPDATE_ERROR.toString());
            throw new RuntimeException(DBErrorMsg.UPDATE_ERROR.toString());
        }
    }

    public void delete(Long inquiryId, String deleter) {

        try{
            if( !checkMine(inquiryId, deleter))
                throw new SecurityException();

            classInquiryRepository.delete(inquiryId);
        } catch (SecurityException e) {
            log.error(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
            throw new SecurityException(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
        } catch (RuntimeException e) {
            log.error(DBErrorMsg.DELETE_ERROR.toString());
            throw new RuntimeException(DBErrorMsg.DELETE_ERROR.toString());
        }
    }

    private boolean checkMine(Long classInquiryId, String userId) {

        ClassInquiryDto dto = classInquiryRepository.findById(classInquiryId);

        if(dto.getCreatedBy().equals(userId))
            return true;

        return false;
    }
}
