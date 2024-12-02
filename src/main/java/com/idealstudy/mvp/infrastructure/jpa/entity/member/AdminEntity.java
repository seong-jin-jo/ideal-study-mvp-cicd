package com.idealstudy.mvp.infrastructure.jpa.entity.member;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "Admin")
@Table(name = "ADMIN")
@DiscriminatorValue("A")
@SuperBuilder
@Getter
@Setter
public class AdminEntity extends MemberEntity{

    // nothing

    public AdminEntity() {

    }
}
