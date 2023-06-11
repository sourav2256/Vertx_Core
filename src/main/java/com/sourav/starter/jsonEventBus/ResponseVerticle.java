package com.sourav.starter.jsonEventBus;

import com.sourav.starter.eventLoop.Worker;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class ResponseVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(Worker.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    EventBus eventBus = vertx.eventBus();
    eventBus.<JsonObject>consumer(RequestVerticle.ADDRESS, message -> {
      LOG.info("Received Message: "+ message.body());
      message.reply(new JsonArray().add("one").add("two").add("three"));
    });
  }
}
