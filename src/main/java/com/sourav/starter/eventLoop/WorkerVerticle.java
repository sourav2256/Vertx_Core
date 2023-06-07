package com.sourav.starter.eventLoop;

import com.sourav.starter.core.MainVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class WorkerVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOG.debug("Deployed as worker verticle.");
    startPromise.complete();
    Thread.sleep(5000);
  }
}
