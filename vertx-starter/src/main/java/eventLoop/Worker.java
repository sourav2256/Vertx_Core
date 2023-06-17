package eventLoop;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class Worker extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(Worker.class);
  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new Worker());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(new WorkerVerticle(),
      new DeploymentOptions()
        .setWorker(true)
        .setWorkerPoolSize(1)
        .setWorkerPoolName("my-worker-verticle"));
    startPromise.complete();
    executeBlockingCode();
  }

  private void executeBlockingCode() {
    vertx.executeBlocking(event -> {
      LOG.info("Executing blocking code");
      try {
        Thread.sleep(5000);
        event.complete();
      } catch (InterruptedException e) {
        LOG.info("Failed: ", e);
        event.fail(e);
      }
    }, result -> {
      if (result.succeeded()) {
        LOG.info("Blocking call done.");
      } else {
        LOG.info("Blocking call failed due to:", result.cause());
      }
    });
  }
}
