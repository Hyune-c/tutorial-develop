package com.example.encryptconverter.domain.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.encryptconverter.domain.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  @Test
  public void create() {
    // given
    Member member = new Member("hyune@gmail.com", "1234");

    // when
    memberRepository.save(member);

    // then
    assertThat(member.getPassword()).isEqualTo(memberRepository.findAll().get(0).getPassword());
  }
}
