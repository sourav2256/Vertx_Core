package com.sourav.vertx_stock_broker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
  public static final int PORT = 8888;

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
    router.route()
      .handler(BodyHandler.create()
        .setBodyLimit(1000)
        .setHandleFileUploads(true)
      )
      .failureHandler(errorContext -> {
      if (errorContext.response().ended()) {
        // Ignore (client closes the connection)
        return;
      }
      LOG.error("Router Error: "+ errorContext.failure());
      errorContext.response()
        .setStatusCode(500)
        .end(new JsonObject().put("message", "Something went wrong").toBuffer());
    });
    AssetsRestAPI.attach(router);
    QuotesRestAPI.attach(router);
    WatchListRestAPI.attach(router);

    vertx.createHttpServer()
      .requestHandler(router)
      .exceptionHandler(error -> LOG.error("HTTP server error: ", error))
      .listen(PORT, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
