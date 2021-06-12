package com.example.mongodb.marketanalysis.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document("predict")
public class Predict {

  @Field(name = "BEVERAGE")
  private Long beverage;

  @Field(name = "CAFE")
  private Long cafe;

  @Field(name = "CHICKEN")
  private Long chicken;

  @Field(name = "CHINESE_FOOD")
  private Long chineseFood;

  @Field(name = "DESSERT")
  private Long dessert;

  @Field(name = "ETC_FOOD")
  private Long etcFood;

  @Field(name = "ETC_FOREIGN_FOOD")
  private Long etcForeignFood;

  @Field(name = "FAST_FOOD")
  private Long fastFood;

  @Field(name = "ICE_CREAM")
  private Long iceCream;

  @Field(name = "JAPANESE_FOOD")
  private Long japaneseFood;

  @Field(name = "KOREAN_FOOD")
  private Long koreanFood;

  @Field(name = "PIZZA")
  private Long pizza;

  @Field(name = "PUB")
  private Long pub;

  @Field(name = "SNACK")
  private Long snack;

  @Field(name = "WESTERN_FOOD")
  private Long westernFood;
}

