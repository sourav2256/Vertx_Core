package com.sourav.vertx_stock_broker;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class WatchListRestAPI {
  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
  static void attach(Router router) {
    HashMap<UUID, WatchList> watchListPerAccount = new HashMap<>();

    String path = "/account/watchlist/:accountID";
    router.get(path).handler(context -> {
      final String accountID = context.pathParam("accountID");
      LOG.info("Path " + context.normalizedPath() + " for account " + accountID);
      Optional<WatchList> watchList = Optional.ofNullable(watchListPerAccount.get(UUID.fromString(accountID)));
      if(watchList.isEmpty()) {
        context.response()
          .setStatusCode(HttpResponseStatus.NOT_FOUND.code())
          .end(new JsonObject()
            .put("message", "watchlist for account " + accountID + " not available!")
            .put("path", context.normalizedPath())
            .toBuffer());
        return;
      }
      context.response().end(watchList.get().toJsonObject().toBuffer());
    });
    router.put(path).handler(context -> {
      final String accountID = context.pathParam("accountID");
      LOG.info("Path " + context.normalizedPath() + " for account " + accountID);
      JsonObject bodyAsJson = context.getBodyAsJson();
      WatchList watchList = bodyAsJson.mapTo(WatchList.class);
      watchListPerAccount.put(UUID.fromString(accountID), watchList);
      context.response().end(bodyAsJson.toBuffer());
    });

  }
}
