package com.sourav.vertx_stock_broker;

import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchList {
  List<Asset> assetList;

  public JsonObject toJsonObject() {
    return JsonObject.mapFrom(this);
  }
}
