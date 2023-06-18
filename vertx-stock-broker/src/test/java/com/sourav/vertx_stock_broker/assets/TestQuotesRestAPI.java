package com.sourav.vertx_stock_broker.assets;

import com.sourav.vertx_stock_broker.MainVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class TestQuotesRestAPI {
  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  void return_quotes_for_assest(Vertx vertx, VertxTestContext testContext) throws Throwable {
    WebClient webClient = WebClient.create(vertx,
                              new WebClientOptions().setDefaultPort(MainVerticle.PORT));
    webClient.get("/quotes/AMZN")
        .send()
          .onComplete(testContext.succeeding(response -> {
            JsonObject jsonObject = response.bodyAsJsonObject();
            LOG.info("Response: "+ jsonObject);
            assertEquals("{\"name\":\"AMZN\"}", jsonObject.getJsonObject("asset").encode());
            assertEquals(200, response.statusCode());
            testContext.completeNow();
          }));

  }

  @Test
  void return_quotes_for_unknown_assest(Vertx vertx, VertxTestContext testContext) throws Throwable {
    WebClient webClient = WebClient.create(vertx,
      new WebClientOptions().setDefaultPort(MainVerticle.PORT));
    webClient.get("/quotes/UNKNOWN")
      .send()
      .onComplete(testContext.succeeding(response -> {
        JsonObject jsonObject = response.bodyAsJsonObject();
        LOG.info("Response: "+ jsonObject);
        assertEquals("{\"message\":\"quote for asset UNKNOWN not available!\",\"path\":\"/quotes/UNKNOWN\"}", jsonObject.encode());
        assertEquals(404, response.statusCode());
        testContext.completeNow();
      }));

  }
}
