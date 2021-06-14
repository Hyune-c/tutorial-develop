package com.example.encryptconverter.domain.member.entity;

import com.example.encryptconverter.common.converter.EncryptConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_user")
@Entity
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_user_id")
  private Long id;

  @Column(name = "email", nullable = false)
  private String email;

  @Getter
  @Convert(converter = EncryptConverter.class)
  @Column(name = "password", nullable = false)
  private String password;

  public Member(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
