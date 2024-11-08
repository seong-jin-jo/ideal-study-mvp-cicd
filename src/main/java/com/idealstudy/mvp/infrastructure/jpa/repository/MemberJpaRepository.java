package com.idealstudy.mvp.infrastructure.jpa.repository;

import com.idealstudy.mvp.infrastructure.jpa.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, String> {
}
