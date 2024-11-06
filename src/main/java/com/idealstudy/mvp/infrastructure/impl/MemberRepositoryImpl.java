package com.idealstudy.mvp.infrastructure.impl;

import com.idealstudy.mvp.infrastructure.MemberRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.MemberJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    @Autowired
    private MemberJpaRepository memberJpaRepository;



}
