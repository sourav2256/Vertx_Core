package com.sourav.starter.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleAB extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("Start " + getClass().getName());
    startPromise.complete();
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {
    System.out.println("Stop " + getClass().getName());
    stopPromise.complete();
  }
}
