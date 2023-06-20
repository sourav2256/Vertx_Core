package com.sourav.vertx_stock_broker;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.Router;

import java.util.Arrays;
import java.util.List;

public class AssetsRestAPI {
  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
  public static final List<String> ASSETS = Arrays.asList("AAPL", "AMEX", "NFLX", "TSLA", "AMZN");

  static void attach(Router router) {
    router.get("/assets").handler(context -> {
      final JsonArray response = new JsonArray();
      ASSETS.stream().map(Asset::new).forEach(response::add);
      // ASSETS.stream().map(name -> new Asset(name)).forEach(value -> response.add(value));
      LOG.info("Path "+ context.normalizedPath() +" response with "+ response.encode());
      context.response()
        .putHeader(HttpHeaders.CONTENT_TYPE.toString(), HttpHeaderValues.APPLICATION_JSON.toString())
        .end(response.toBuffer());
    });
  }

}
