package com.idealstudy.mvp.infrastructure.jpa.repository.classroom;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.FAQEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQJpaRepository extends JpaRepository<FAQEntity, Long> {
}
