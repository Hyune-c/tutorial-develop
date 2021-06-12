package com.example.jwtsecurity.core.service;

import com.example.jwtsecurity.core.service.dto.CoffeeDTO;
import java.util.List;
import java.util.Optional;

public interface CoffeeUseCase {

  Optional<List<CoffeeDTO>> findAll();
}
