package com.idealstudy.mvp.infrastructure.jpa.repository.classroom;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassInquiryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassInquiryJpaRepository extends JpaRepository<ClassInquiryEntity, Long> {

    Page<ClassInquiryEntity> findByClassroom_classroomId(String classroomId, Pageable pageable);
}
