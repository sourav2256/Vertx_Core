package eventLoop;

import io.vertx.core.*;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class EventLoopExample extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(EventLoopExample.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx(
      new VertxOptions()
        .setMaxEventLoopExecuteTime(500)
        .setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS)
        .setBlockedThreadCheckInterval(1)
        .setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS)
    );
    //vertx.deployVerticle(new EventLoopExample());
    vertx.deployVerticle(EventLoopExample.class.getName(),
      new DeploymentOptions().setInstances(4));
  }

  @Override
  public void start(Promise<Void> startPromise) throws InterruptedException {
    // LOG.debug("Start {}",getClass().getName());
    startPromise.complete();
    // Do not do this inside a verticle
    Thread.sleep(5000);
  }
}
