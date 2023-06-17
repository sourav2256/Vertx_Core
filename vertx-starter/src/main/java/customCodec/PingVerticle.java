package customCodec;

import eventLoop.Worker;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class PingVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(Worker.class);
  public static final String ADDRESS = PingVerticle.class.getName();

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    EventBus eventBus = vertx.eventBus();
    final Ping message = new Ping("Hello", true);
    LOG.info("Sending: " + message);
    // Registered only once
    eventBus.registerDefaultCodec(Ping.class, new LocalMessageCodec<>(Ping.class));
    eventBus.<Pong>request(ADDRESS, message, reply -> {
      if(reply.failed()) {
        LOG.error("Failed: "+ reply.cause());
        return;
      }
      LOG.info("Response: " + reply.result().body());
    });
    startPromise.complete();
  }
}
