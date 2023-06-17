package com.sourav.vertx_stock_broker;

public class Asset {
  private final String name;

  public Asset(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
