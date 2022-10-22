package dev.luke.handler;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class HelloHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        System.out.println("handle");
        context.result("How are you doing today?");
    }
}
