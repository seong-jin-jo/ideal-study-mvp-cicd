package com.idealstudy.mvp.infrastructure.jpa.entity.member;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("S")
public class StudentEntity extends MemberEntity {

    // Classroom과 N:M 관계
    @ManyToMany(mappedBy = "students")
    private List<ClassroomEntity> classrooms; // 참여한 수업 목록

}
