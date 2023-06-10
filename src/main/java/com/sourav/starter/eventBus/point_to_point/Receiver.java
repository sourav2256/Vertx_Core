package com.sourav.starter.eventBus.point_to_point;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class Receiver extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(Sender.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    vertx.eventBus().<String>consumer(Sender.class.getName(), message -> {
      LOG.info("Received: "+ message.body());
    });
  }
}
