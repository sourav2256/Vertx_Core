package com.sourav.starter.core;

import com.sourav.starter.eventLoop.Worker;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
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
        LOG.info("Result: "+result);
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
      .onSuccess(context::failNow)
      .onFailure(error -> {
        LOG.info("Result: "+error);
        context.completeNow();
      });
    LOG.info("End");
  }
}
