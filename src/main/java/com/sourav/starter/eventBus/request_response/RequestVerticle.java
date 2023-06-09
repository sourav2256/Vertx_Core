package com.sourav.starter.eventBus.request_response;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class RequestVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    vertx.eventBus();
  }
}
