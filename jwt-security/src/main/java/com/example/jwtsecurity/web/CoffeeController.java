package com.example.jwtsecurity.web;

import com.example.jwtsecurity.domain.coffee.dto.CoffeeDTO;
import com.example.jwtsecurity.domain.coffee.service.CoffeeService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/coffees")
@RequiredArgsConstructor
public class CoffeeController {

  private final CoffeeService coffeeService;

  @GetMapping
  public List<CoffeeDTO> getAllCoffees() {
    return coffeeService.findAll()
        .orElse(Collections.emptyList());
  }
}
