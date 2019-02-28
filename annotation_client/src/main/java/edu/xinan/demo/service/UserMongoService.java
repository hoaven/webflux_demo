package edu.xinan.demo.service;

import edu.xinan.demo.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by hoaven on 2019/2/28
 */
@Component
public class UserMongoService {

  @Autowired
  WebClient webClient;

  public Flux<User> list() {
    return webClient
        .get()
        .uri("http://annotation-program/user/mongo/list")
        .retrieve()
        .bodyToFlux(User.class);
  }

  public Mono<User> getById(String id) {
    return webClient
        .get()
        .uri("http://annotation-program/user/mongo/single/{id}", id)
        .retrieve()
        .bodyToMono(User.class);
  }

  public Mono<User> create(User user) {
    return webClient
        .post()
        .uri("http://annotation-program/user/mongo")
        .body(Mono.just(user), User.class)
        .retrieve()
        .bodyToMono(User.class);
  }
}
