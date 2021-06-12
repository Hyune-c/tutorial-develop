package com.example.jwtsecurity.provider.service;

import com.example.jwtsecurity.core.service.CoffeeUseCase;
import com.example.jwtsecurity.core.service.dto.CoffeeDTO;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CoffeeService implements CoffeeUseCase {

  @Override
  public Optional<List<CoffeeDTO>> findAll() {
    return Optional.of(
        Arrays.asList(CoffeeDTO.builder().name("latte").price(1200).build(), CoffeeDTO.builder().name("americano").price(900).build())
    );
  }
}
