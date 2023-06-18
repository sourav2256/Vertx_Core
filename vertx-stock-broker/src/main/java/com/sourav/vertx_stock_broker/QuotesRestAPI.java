package com.sourav.vertx_stock_broker;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

      Optional<Quote> quote = Optional.ofNullable(cachedQuotes.get(assestParam));
      if(quote.isEmpty()) {
        context.response()
          .setStatusCode(HttpResponseStatus.NOT_FOUND.code())
          .end(new JsonObject()
          .put("message", "quote for asset " + assestParam + " not available!")
            .put("path", context.normalizedPath())
            .toBuffer());
        return;
      }
      JsonObject response = quote.get().toJsonObject();
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
