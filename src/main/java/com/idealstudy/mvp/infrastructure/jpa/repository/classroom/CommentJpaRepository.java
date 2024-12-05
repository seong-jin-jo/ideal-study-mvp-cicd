package com.idealstudy.mvp.infrastructure.jpa.repository.classroom;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<ReplyEntity, Long> {
}
