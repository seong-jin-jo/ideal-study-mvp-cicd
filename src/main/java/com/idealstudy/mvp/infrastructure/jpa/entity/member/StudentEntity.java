package com.idealstudy.mvp.infrastructure.jpa.entity.member;

import com.idealstudy.mvp.enums.member.Grade;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import java.util.List;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "Student")
@Table(name = "STUDENT")
@DiscriminatorValue("S")
@SuperBuilder
@Getter
@Setter
public class StudentEntity extends MemberEntity {

    private String school;

    private Grade grade;

    // Classroom과 N:M 관계
    @ManyToMany(mappedBy = "students")
    private List<ClassroomEntity> classrooms; // 참여한 수업 목록

    public StudentEntity() {
        super();
    }
}
