package com.sourav.starter.jsonEventBus;

import io.vertx.core.Vertx;

public class RequestResponseExampleJSON {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());
  }
}
