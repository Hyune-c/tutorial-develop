package com.example.jwtsecurity.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
@Entity
public class Member {

  @JsonIgnore
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
  @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
  private Long id;

//  @Column(name = "USERNAME", length = 50, unique = true)
//  @NotNull
//  @Size(min = 4, max = 50)
//  private String username;

  @JsonIgnore
  @Column(name = "PASSWORD", length = 100)
  @NotNull
  @Size(min = 4, max = 100)
  private String password;

  @Column(name = "EMAIL", length = 50, unique = true)
  @NotNull
  @Size(min = 4, max = 50)
  private String email;

  @Column(name = "ROLE", length = 50)
  @NotNull
  @Size(min = 4, max = 50)
  private String role;
}
