package com.example.jwtsecurity.domain.coffee.service;

import com.example.jwtsecurity.domain.coffee.dto.CoffeeDTO;
import java.util.List;
import java.util.Optional;

public interface CoffeeUseCase {

  Optional<List<CoffeeDTO>> findAll();
}
