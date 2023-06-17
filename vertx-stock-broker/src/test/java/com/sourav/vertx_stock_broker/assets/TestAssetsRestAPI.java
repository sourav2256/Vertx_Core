package com.sourav.vertx_stock_broker.assets;

import com.sourav.vertx_stock_broker.MainVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class TestAssetsRestAPI {
  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  void return_all_assets_test(Vertx vertx, VertxTestContext testContext) throws Throwable {
    WebClient webClient = WebClient.create(vertx,
                              new WebClientOptions().setDefaultPort(MainVerticle.PORT));
    webClient.get("/assets")
        .send()
          .onComplete(testContext.succeeding(response -> {
            JsonArray jsonArray = response.bodyAsJsonArray();
            LOG.info("Response: "+ jsonArray);
            assertEquals("[{\"name\":\"AAPL\"},{\"name\":\"AMEX\"},{\"name\":\"NFLX\"},{\"name\":\"TSLA\"}]", jsonArray.encode());
            assertEquals(200, response.statusCode());
            testContext.completeNow();
          }));

  }
}
