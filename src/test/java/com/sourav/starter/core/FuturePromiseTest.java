package com.sourav.starter.core;

import com.sourav.starter.eventLoop.Worker;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class FuturePromiseTest {
  private static final Logger LOG = LoggerFactory.getLogger(FuturePromiseTest.class);

  @Test
  void promise_success(Vertx vertx, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    LOG.info("Start");
    vertx.setTimer(500, id -> {
      promise.complete("Success");
      LOG.info("Success");
      context.completeNow();
    });
    LOG.info("End");
  }

  @Test
  void promise_failure(Vertx vertx, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    LOG.info("Start");
    vertx.setTimer(500, id -> {
      promise.fail(new RuntimeException("Failed!"));
      LOG.info("Failed");
      context.completeNow();
    });
    LOG.info("End");
  }

  @Test
  void future_success(Vertx vertx, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    LOG.info("Start");
    vertx.setTimer(500, id -> {
      promise.complete("Success");
      LOG.info("Timer Done!");
      context.completeNow();
    });
    Future<String> future = promise.future();
    future
      .onSuccess(result -> {
        LOG.info("Result: " + result);
        context.completeNow();
      })
      .onFailure(context::failNow);
    LOG.info("End");
  }

  @Test
  void future_failure(Vertx vertx, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    LOG.info("Start");
    vertx.setTimer(500, id -> {
      promise.fail(new RuntimeException("Failed!"));
      LOG.info("Timer Done!");
    });
    Future<String> future = promise.future();
    future
      .onSuccess(result -> {
        context.completeNow();
      })
      .onFailure(error -> {
        LOG.info("Result: " + error);
        context.completeNow();
      });
    LOG.info("End");
  }

  @Test
  void future_map(Vertx vertx, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    LOG.info("Start");
    vertx.setTimer(500, id -> {
      promise.complete("Success");
      LOG.info("Timer Done!");
      context.completeNow();
    });
    Future<String> future = promise.future();
    future
      .map(asString -> {
        LOG.info("Map String to JsonObject");
        return new JsonObject().put("key", asString);
      })
      .map(jssonObject -> new JsonArray().add(jssonObject))
      .onSuccess(result -> {
        LOG.info("Result: " + result + " of type " + result.getClass().getSimpleName());
        context.completeNow();
      })
      .onFailure(context::failNow);
    LOG.info("End");
  }

  @Test
  void future_coordination(Vertx vertx, VertxTestContext context) {
    vertx.createHttpServer()
      .requestHandler(request -> LOG.info(" " + request))
      .listen(10_000)
      .compose(server -> {
        LOG.info("Another Task");
        return Future.succeededFuture(server);
      })
      .compose(server -> {
        LOG.info("Even more");
        return Future.succeededFuture(server);
      })
      .onFailure(context::failNow)
      .onSuccess(server -> {
        LOG.info("Server started on port "+server.actualPort());
        context.completeNow();
      });
  }

  @Test
  void future_composition(Vertx vertx, VertxTestContext context) {
    var one = Promise.<Void>promise();
    var two = Promise.<Void>promise();
    var three = Promise.<Void>promise();

    var futureOne = one.future();
    var futureTwo = two.future();
    var futureThree = three.future();

    CompositeFuture.any(futureOne, futureTwo, futureThree)
      .onFailure(context::failNow)
      .onSuccess(result -> {
        LOG.info("Success");
        context.completeNow();
      });

    // complete futures

    vertx.setTimer(500, id -> {
      one.complete();
      two.complete();
      three.fail("Three Failed");
    });
  }
}
