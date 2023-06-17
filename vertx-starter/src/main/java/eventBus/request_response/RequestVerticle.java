package eventBus.request_response;

import eventLoop.Worker;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class RequestVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(Worker.class);
  public static final String ADDRESS = "my.request.address";

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    EventBus eventBus = vertx.eventBus();
    String message = "Hello World!";
    LOG.info("Sending: " + message);
    eventBus.<String>request(ADDRESS, message, reply -> {
      LOG.info("Response: " + reply.result().body());
    });
  }
}
