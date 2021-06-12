package com.example.mongodb.controller;

import com.example.mongodb.marketanalysis.document.MarketAnalysis;
import com.example.mongodb.marketanalysis.repository.MarketAnalysisRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarketAnalysisController {

  private final MarketAnalysisRepository marketAnalysisRepository;

  public MarketAnalysisController(MarketAnalysisRepository marketAnalysisRepository) {
    this.marketAnalysisRepository = marketAnalysisRepository;
  }

  @GetMapping("/api/market-analysis")
  public List<MarketAnalysis> findAll() {
    return marketAnalysisRepository.findAll();
  }
}
