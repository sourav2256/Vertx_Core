package com.sourav.vertx_stock_broker.wishlist;

import com.sourav.vertx_stock_broker.Asset;
import com.sourav.vertx_stock_broker.MainVerticle;
import com.sourav.vertx_stock_broker.WatchList;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class TestQuotesRestAPI {
  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  void add_and_returns_watchlist_for_account_test(Vertx vertx, VertxTestContext testContext) throws Throwable {
    WebClient webClient = WebClient.create(vertx,
      new WebClientOptions().setDefaultPort(MainVerticle.PORT));
    UUID accountID = UUID.randomUUID();
    webClient.put("/account/watchlist/" + accountID.toString())
      .sendJsonObject(requestBody())
      .onComplete(testContext.succeeding(response -> {
        JsonObject jsonObject = response.bodyAsJsonObject();
        LOG.info("Response: " + jsonObject);
        assertEquals("{\"name\":\"AMZN\"}", jsonObject.getJsonObject("asset").encode());
        assertEquals(200, response.statusCode());
        testContext.completeNow();
      }));

  }

  private static JsonObject requestBody() {
    return new WatchList(
      Arrays.asList(
        new Asset("AMZN"),
        new Asset("TSLA")))
      .toJsonObject();
  }
}
