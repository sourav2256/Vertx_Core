package eventBus.request_response;

import io.vertx.core.Vertx;

public class RequestResponseExample {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());
  }
}
