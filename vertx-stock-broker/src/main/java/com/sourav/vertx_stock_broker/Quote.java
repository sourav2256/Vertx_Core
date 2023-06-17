package com.sourav.vertx_stock_broker;

import io.vertx.core.json.JsonObject;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class Quote {
  Asset asset;
  BigDecimal bid;
  BigDecimal ask;
  BigDecimal lastPrice;
  BigDecimal volumn;

  public JsonObject toJsonObject() {
    return JsonObject.mapFrom(this);
  }
}
