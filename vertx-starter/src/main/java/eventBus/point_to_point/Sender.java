package eventBus.point_to_point;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class Sender extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(Sender.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    vertx.setPeriodic(1000, event -> {
      vertx.eventBus().send(Sender.class.getName(), "Sending a message......");
    });
  }
}
