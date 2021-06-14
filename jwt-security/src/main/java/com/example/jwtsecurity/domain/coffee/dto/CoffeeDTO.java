package com.example.jwtsecurity.domain.coffee.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoffeeDTO {

  private final String name;
  private final Integer price;
}
