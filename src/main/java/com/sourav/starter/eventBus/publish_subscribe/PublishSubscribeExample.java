package com.sourav.starter.eventBus.publish_subscribe;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class PublishSubscribeExample {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Publish());
    vertx.deployVerticle(new Subscriber1());
    vertx.deployVerticle(Subscriber2.class.getName(), new DeploymentOptions().setInstances(2));
  }
}
