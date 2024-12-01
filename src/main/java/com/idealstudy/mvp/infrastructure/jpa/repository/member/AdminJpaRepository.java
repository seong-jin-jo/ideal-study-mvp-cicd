package com.idealstudy.mvp.infrastructure.jpa.repository.member;

import com.idealstudy.mvp.infrastructure.jpa.entity.member.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminJpaRepository extends JpaRepository<AdminEntity, String> {
}
