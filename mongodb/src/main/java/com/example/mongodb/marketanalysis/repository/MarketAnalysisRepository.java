package com.example.mongodb.marketanalysis.repository;

import com.example.mongodb.marketanalysis.document.MarketAnalysis;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketAnalysisRepository extends MongoRepository<MarketAnalysis, String> {

}
