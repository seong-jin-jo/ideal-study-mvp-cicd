package com.idealstudy.mvp.infrastructure.jpa.repository;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomJpaRepository extends JpaRepository<ClassroomEntity, String> {
}