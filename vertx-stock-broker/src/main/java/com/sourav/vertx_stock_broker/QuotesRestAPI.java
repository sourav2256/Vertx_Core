package com.sourav.vertx_stock_broker;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class QuotesRestAPI {
  private static final Logger LOG = LoggerFactory.getLogger(QuotesRestAPI.class);

  public static void attach(Router router) {
    Map<String, Quote> cachedQuotes = new HashMap<>();
    AssetsRestAPI.ASSETS.forEach(asset -> {
      cachedQuotes.put(asset, initRandomQuote(asset));
    });

    router.get("/quotes/:asset").handler(context -> {
      final String assestParam = context.pathParam("asset");
      LOG.info("Asset parameter: " + assestParam);

      Quote quote = cachedQuotes.get(assestParam);
      JsonObject response = quote.toJsonObject();
      LOG.info("Path " + context.normalizedPath() + " response with " + response.encode());
      context.response().end(response.toBuffer());
    });
  }

  private static Quote initRandomQuote(String assetParam) {
    return Quote.builder()
      .asset(new Asset(assetParam))
      .volumn(randomValue())
      .bid(randomValue())
      .ask(randomValue())
      .lastPrice(randomValue())
      .build();
  }

  private static BigDecimal randomValue() {
    return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 100));
  }
}
