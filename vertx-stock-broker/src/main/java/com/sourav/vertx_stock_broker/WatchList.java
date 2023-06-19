package com.sourav.vertx_stock_broker;

import io.vertx.core.json.JsonObject;
import lombok.Value;

import java.util.List;

@Value
public class WatchList {
  List<Asset> assetList;

  public JsonObject toJsonObject() {
    return JsonObject.mapFrom(this);
  }
}
