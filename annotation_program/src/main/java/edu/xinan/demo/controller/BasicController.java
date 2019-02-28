/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.xinan.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author hoaven
 */
@RestController
@RequestMapping("/basic")
public class BasicController {

  @GetMapping("/hello")
  public Mono<String> sayHelloWorld() {
    return Mono.just("Hello World");
  }

  @GetMapping(value = "/single", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Mono<Integer> getSingle() {
    return Mono.just(0)
        .log("Get single success");
  }

  @GetMapping(value = "/array", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<Integer> getArray() {
    return Flux.just(1, 2, 3, 4, 5)
        .log("Get array success");
  }
}

