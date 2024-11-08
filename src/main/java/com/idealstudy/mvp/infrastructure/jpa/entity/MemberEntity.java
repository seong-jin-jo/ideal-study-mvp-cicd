package com.idealstudy.mvp.infrastructure.jpa.entity;

import com.idealstudy.mvp.enums.member.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(length = 15, nullable = false)
    private String phoneAddress;

    @Column(length = 100, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender sex;

    @Column(columnDefinition = "char(36)")
    private String referralId;

    @Column(columnDefinition = "INT default 0")
    private Integer level;

    @Enumerated(EnumType.STRING)
    private Role role;
}
