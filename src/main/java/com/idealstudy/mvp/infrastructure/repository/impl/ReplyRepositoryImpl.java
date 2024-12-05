package com.idealstudy.mvp.infrastructure.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ReplyEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.ReplyJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.PostJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.preclass.ClassInquiryJpaRepository;
import com.idealstudy.mvp.infrastructure.repository.ReplyRepository;
import com.idealstudy.mvp.mapstruct.ReplyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.idealstudy.mvp.application.dto.ReplyDto;
import com.idealstudy.mvp.enums.classroom.Visibility;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepository {

    @Autowired
    private final ReplyJpaRepository replyJpaRepository;

    @Autowired
    private final ClassInquiryJpaRepository classInquiryJpaRepository;

    @Autowired
    private final PostJpaRepository postJpaRepository;
    
    @Override
    public ReplyDto create(String content, Visibility visibility, Long parentCommentId, Long classInquiryId, 
        Long postId, String createdBy) {

        ReplyEntity entity = ReplyEntity.builder()
        .content(content)
        .visibility(visibility)
        .createdBy(createdBy)
        .build();

        if(parentCommentId != null) {
            entity.setParentComment(replyJpaRepository.findById(parentCommentId).orElseThrow());
        }

        if(classInquiryId != null) {
            entity.setClassInquiry(classInquiryJpaRepository.findById(classInquiryId).orElseThrow());
        }
        if(postId != null) {
            entity.setPost(postJpaRepository.findById(postId).orElseThrow());
        }

        ReplyEntity savedEntity = replyJpaRepository.save(entity);

        return ReplyMapper.INSTANCE.entityToDto(savedEntity);
    }

    @Override
    public ReplyDto findById(Long id) {

        ReplyEntity entity = replyJpaRepository.findById(id).orElseThrow();

        return ReplyMapper.INSTANCE.entityToDto(entity);
    }

    @Override
    public Page<ReplyDto> findList(Pageable pageable) {
        return null;
    }

    /**
     * content, visibility 중 update할 일 없으면 null을 넣어주세요.
     */
    @Override
    public ReplyDto update(Long id, String content, Visibility visibility) {

        ReplyEntity entity = replyJpaRepository.findById(id).orElseThrow();

        if(content != null) {
            entity.setContent(content);
        }
        if(visibility != null) {
            entity.setVisibility(visibility);
        }

        ReplyEntity savedEntity = replyJpaRepository.save(entity);

        return ReplyMapper.INSTANCE.entityToDto(savedEntity);
    }

    @Override
    public void delete(Long id) {
        replyJpaRepository.deleteById(id);
    }
}
