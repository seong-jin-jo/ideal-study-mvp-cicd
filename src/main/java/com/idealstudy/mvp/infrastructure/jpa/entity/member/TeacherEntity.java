package com.idealstudy.mvp.infrastructure.jpa.entity.member;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("T")
public class TeacherEntity extends MemberEntity {

    // Classroom과 1:N 관계
    @OneToMany(mappedBy = "teacher")
    private List<ClassroomEntity> classrooms; // 참여한 클래스 목록

}
