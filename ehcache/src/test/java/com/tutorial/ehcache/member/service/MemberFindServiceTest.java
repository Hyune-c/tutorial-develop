package com.tutorial.ehcache.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.tutorial.ehcache.member.entity.Member;
import com.tutorial.ehcache.member.repository.MemberRepository;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;

@Transactional
@SpringBootTest
class MemberFindServiceTest {

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private MemberFindService memberFindService;

  @Autowired
  private MemberRepository memberRepository;

  private static StopWatch stopWatch;
  private static String result = "";

  private int loopCount;
  private String name;

  @PostConstruct
  public void postConstruct() {
    stopWatch = new StopWatch("enum 조회 테스트");

    loopCount = 3;
    memberRepository.deleteAll();
    name = "dan";
    memberRepository.save(new Member(name, "abc@gmail.com"));
  }

  @AfterAll
  public static void afterAll() {
    System.out.println(result);
  }

  @Test
  public void cache() {
    // given
    stopWatch.start("cache");

    // when
    for (int i = 0; i < loopCount; i++) {
      em.clear();
      memberFindService.findByNameCache(name);
    }

    // then
    stopWatch.stop();
    TaskInfo taskInfo = stopWatch.getLastTaskInfo();
    assertThat(taskInfo.getTimeSeconds()).isLessThan(loopCount);
    result += String.format("### Elapsed Time %s: %s s%n", taskInfo.getTaskName(), taskInfo.getTimeSeconds());
  }

  @Test
  public void noCache() {
    // given
    stopWatch.start("noCache");

    // when
    for (int i = 0; i < loopCount; i++) {
      em.clear();
      memberFindService.findByNameNoCache(name);
    }

    // then
    stopWatch.stop();
    TaskInfo taskInfo = stopWatch.getLastTaskInfo();
    assertThat(taskInfo.getTimeSeconds()).isGreaterThan(loopCount);
    result += String.format("### Elapsed Time %s: %s s%n", taskInfo.getTaskName(), taskInfo.getTimeSeconds());
  }

  @Test
  public void refresh() {
    // given
    stopWatch.start("refresh");

    // when
    for (int i = 0; i < loopCount; i++) {
      em.clear();
      memberFindService.refresh(name);
      memberFindService.findByNameCache(name);
    }

    // then
    stopWatch.stop();
    TaskInfo taskInfo = stopWatch.getLastTaskInfo();
    assertThat(taskInfo.getTimeSeconds()).isGreaterThan(loopCount);
    result += String.format("### Elapsed Time %s: %s s%n", taskInfo.getTaskName(), taskInfo.getTimeSeconds());
  }
}
