package de.codepitbull.vertx.kubernetes.blueprint;

import io.vertx.reactivex.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        vertx.eventBus().<Long>consumer("ping", msg -> {
            System.out.println("I AM (" + hashCode() + ") and talked to(" + msg.body() + ")");
            msg.reply(hashCode());
        });
        vertx
                .createHttpServer()
                .requestHandler(r -> {
                    vertx.eventBus().<Long>send("ping", hashCode(), response -> {
                        if (response.succeeded()) {
                            r.response().end("I AM (" + hashCode() + ") and talked to(" + response.result().body() + ")");
                        } else {
                            r.response().end("I AM (" + hashCode() + ")");
                        }
                    });
                })
                .listen(8666);
    }
}
