package com.idealstudy.mvp.infrastructure.jpa.entity.classroom;

import com.idealstudy.mvp.infrastructure.jpa.entity.BaseEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "class_inquiry")
@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
public class ClassInquiryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private ClassroomEntity classroom;
}
