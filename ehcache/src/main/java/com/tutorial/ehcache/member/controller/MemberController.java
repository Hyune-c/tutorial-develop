package com.tutorial.ehcache.member.controller;

import com.tutorial.ehcache.member.entity.Member;
import com.tutorial.ehcache.member.service.MemberFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberFindService memberFindService;

  @GetMapping("/members/{name}/nocache")
  public Member getNoCacheMember(@PathVariable String name) {
    return memberFindService.findByNameNoCache(name);
  }

  @GetMapping("/members/{name}/cache")
  public Member getCacheMember(@PathVariable String name) {
    return memberFindService.findByNameCache(name);
  }

  @GetMapping("/members/{name}/refresh")
  public void refresh(@PathVariable String name) {
    memberFindService.refresh(name);
  }
}
