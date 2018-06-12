package de.codepitbull.vertx.kubernetes.blueprint;

import io.vertx.reactivex.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(r -> r.response().end("I AM UP")).listen(8666);
    }
}
