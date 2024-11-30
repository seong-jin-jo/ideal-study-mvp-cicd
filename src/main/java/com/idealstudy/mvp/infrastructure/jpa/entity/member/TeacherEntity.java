package com.idealstudy.mvp.infrastructure.jpa.entity.member;

import com.idealstudy.mvp.enums.member.SchoolRegister;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import jakarta.persistence.*;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "Teacher")
@Table(name = "TEACHER")
@DiscriminatorValue("T")
@SuperBuilder
@Getter
@Setter
public class TeacherEntity extends MemberEntity {

    // @Column(length = 50)
    private String univ;

    @Enumerated(EnumType.STRING)
    private SchoolRegister status;

    // @Column(length = 30)
    private String subject;

    // Classroom과 1:N 관계
    @OneToMany(mappedBy = "teacher")
    private List<ClassroomEntity> classrooms; // 참여한 클래스 목록


    /* 기존 엔티티 객체로 새 엔티티 객체 생성 불가
    public TeacherEntity(MemberEntity parent, String univ, SchoolRegister status, String subject) {

        super.setUserId(parent.getUserId());
        super.setPassword(parent.getPassword());
        super.setName(parent.getName());
        super.setPhoneAddress(parent.getPhoneAddress());
        super.setEmail(parent.getEmail());
        super.setSex(parent.getSex());
        super.setReferralId(parent.getReferralId());
        super.setLevel(parent.getLevel());
        super.setRole(parent.getRole());
        super.setIntroduction(parent.getIntroduction());
        super.setProfile(parent.getProfile());
        super.setFromSocial(parent.getFromSocial());
        super.setInit(parent.getInit());
        super.setDeleted(parent.getDeleted());

        this.univ = univ;
        this.status = status;
        this.subject=subject;
    }
     */
}
