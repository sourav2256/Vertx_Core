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
    eventBus.<String>consumer(RequestVerticle.ADDRESS, message -> {
      LOG.info("Received Message: "+ message.body());
      message.reply("Received your message. Thanks!");
    });
  }
}
