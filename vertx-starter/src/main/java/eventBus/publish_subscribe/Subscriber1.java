package eventBus.publish_subscribe;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class Subscriber1 extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(Subscriber1.class);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    vertx.eventBus().<String>consumer(Publish.class.getName(), message -> {
      LOG.info("Received: " + message.body());
    });
  }
}
