package com.sourav.starter.eventBus.point_to_point;

import io.vertx.core.Vertx;

public class PointToPointExample {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Sender());
    vertx.deployVerticle(new Receiver());
  }
}
