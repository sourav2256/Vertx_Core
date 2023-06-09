package com.sourav.starter.eventBus.request_response;

import com.sourav.starter.eventLoop.Worker;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class ResponseVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(Worker.class);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    EventBus eventBus = vertx.eventBus();
    String message = "Hello World!";
    LOG.info("Sending: "+ message);
    eventBus.request("my.request.address", message, reply -> {
      LOG.info("Response: "+ reply.result());
    });
  }
}
