package jsonEventBus;

import eventLoop.Worker;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class RequestVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(Worker.class);
  public static final String ADDRESS = "my.request.address";

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    EventBus eventBus = vertx.eventBus();
    String message = "Hello World!";
    JsonObject jsonObject = new JsonObject()
        .put("message", message)
        .put("version", 1);
    LOG.info("Sending: " + jsonObject);
    eventBus.<JsonArray>request(ADDRESS, jsonObject, reply -> {
      LOG.info("Response: " + reply.result().body());
    });
  }
}
