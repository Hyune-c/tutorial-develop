package com.example.mongodb.marketanalysis.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document("market_analysis")
public class MarketAnalysis {

  @Id
  private String _id;

  @Field(name = "bd_mgt_sn")
  private String buildingNumber;

  @Field(name = "etl_ym")
  private String yearMonth;

  @Field(name = "predict")
  private Predict predict;
}

