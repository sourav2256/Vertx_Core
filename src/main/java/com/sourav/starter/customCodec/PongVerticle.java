package com.sourav.starter.customCodec;

import com.sourav.starter.eventLoop.Worker;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class PongVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(Worker.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    EventBus eventBus = vertx.eventBus();
    eventBus.registerDefaultCodec(Pong.class, new LocalMessageCodec<>(Pong.class));
    eventBus.<Ping>consumer(PingVerticle.ADDRESS, message -> {
      LOG.info("Received Message: "+ message.body());
      message.reply(new Pong(0));
    }).exceptionHandler(error -> {
      LOG.error("Error: ", error);
    });
    startPromise.complete();
  }
}
