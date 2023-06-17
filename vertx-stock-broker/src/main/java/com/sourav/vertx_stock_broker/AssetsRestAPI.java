package com.sourav.vertx_stock_broker;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.Router;

public class AssetsRestAPI {
  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

  static void attach(Router router) {
    router.get("/assets").handler(context -> {
      final JsonArray response = new JsonArray();
      response
        .add(new Asset("AAPL"))
        .add(new Asset("AMEX"))
        .add(new Asset("NFLX"))
        .add(new Asset("TSLA"));
      LOG.info("Path "+ context.normalizedPath() +" response with "+ response.encode());
      context.response().end(response.toBuffer());
    });
  }

}
