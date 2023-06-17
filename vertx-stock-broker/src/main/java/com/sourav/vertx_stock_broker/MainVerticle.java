package com.sourav.vertx_stock_broker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.exceptionHandler(error -> {
      LOG.error("Unhandled: " + error);
    });
    vertx.deployVerticle(new MainVerticle(), ar -> {
      if (ar.failed()) {
        LOG.error("Failed to deploy: " + ar.cause());
        return;
      }
      LOG.info("Deployed: " + MainVerticle.class.getName());
    });
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Router router = Router.router(vertx);
    router.get("/assets").handler(context -> {
      final JsonArray response = new JsonArray();
      response
        .add(new JsonObject().put("symbol", "AAPL"))
        .add(new JsonObject().put("symbol", "AMEX"))
        .add(new JsonObject().put("symbol", "NFLX"))
        .add(new JsonObject().put("symbol", "TSLA"));
      LOG.info("Path "+ context.normalizedPath() +" response with "+ response.encode());
      context.response().end(response.toBuffer());
    });

    vertx.createHttpServer()
      .requestHandler(router)
      .exceptionHandler(error -> LOG.error("HTTP server error: ", error))
      .listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
