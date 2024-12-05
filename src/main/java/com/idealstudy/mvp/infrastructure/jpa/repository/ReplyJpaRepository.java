package com.idealstudy.mvp.infrastructure.jpa.repository;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyJpaRepository extends JpaRepository<ReplyEntity, Long> {
}
