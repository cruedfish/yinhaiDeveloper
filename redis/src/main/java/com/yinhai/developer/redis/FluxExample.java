package com.yinhai.developer.redis;

import reactor.core.publisher.Flux;

public class FluxExample {
    public static void main(String[]args){
        Flux.just("Ben", "Michael", "Mark") //
                .doOnNext(s -> System.out.println("Hello " + s + "!"))
                .doOnComplete(() -> System.out.println("Completed"))
                .take(2)
                .subscribe();

    }
}
