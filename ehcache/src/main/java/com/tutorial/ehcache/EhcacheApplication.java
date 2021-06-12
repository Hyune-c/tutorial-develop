package com.tutorial.ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class EhcacheApplication {

  public static void main(String[] args) {
    SpringApplication.run(EhcacheApplication.class, args);
  }
}
