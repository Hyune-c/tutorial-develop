package com.tutorial.ehcache.member.service;

import com.tutorial.ehcache.member.entity.Member;
import com.tutorial.ehcache.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberFindService {

  private static final long WAIT_TIME = 1000;

  private final MemberRepository memberRepository;

  public Member findByNameNoCache(String name) {
    waitForSlowQuery();
    return memberRepository.findByName(name).get();
  }

  @Cacheable(value = "findMemberCache", key = "#name")
  public Member findByNameCache(String name) {
    waitForSlowQuery();
    return memberRepository.findByName(name).get();
  }

  @CacheEvict(value = "findMemberCache", key = "#name")
  public void refresh(String name) {
    log.info("### cache clear:{}", name);
  }

  private void waitForSlowQuery() {
    try {
      Thread.sleep(WAIT_TIME);
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
  }
}
