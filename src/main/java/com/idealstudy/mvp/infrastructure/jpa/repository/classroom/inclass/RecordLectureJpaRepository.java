package com.idealstudy.mvp.infrastructure.jpa.repository.classroom.inclass;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.RecordLectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordLectureJpaRepository extends JpaRepository<RecordLectureEntity, Long> {
}
