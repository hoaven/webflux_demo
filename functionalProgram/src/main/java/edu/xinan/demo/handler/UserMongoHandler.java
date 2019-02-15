package edu.xinan.demo.handler;

import edu.xinan.demo.po.User;
import edu.xinan.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Created by hoaven on 2019/2/14
 */
@Component
public class UserMongoHandler {

  @Autowired
  private UserService userMongoService;

  public Mono<ServerResponse> list(ServerRequest request) {
    return ServerResponse.ok().
        body(this.userMongoService.list(), User.class);
  }

  public Mono<ServerResponse> getById(ServerRequest request) {
    return ServerResponse.ok()
        .body(this.userMongoService.getById(request.pathVariable("id")), User.class);
  }

  public Mono<ServerResponse> create(ServerRequest request) {
//    return request.body(BodyExtractors.toMono(Map.class))
//        .flatMap(map -> {
//          User user = new User((String) map.get("id"), (String) map.get("name"), (String) map.get("email"));
//          this.userMongoService.create(user);
//          return ServerResponse.ok().body(BodyInserters.fromObject(user));
//        });

//    return request.body(BodyExtractors.toMono(User.class))
//        .flatMap(user -> {
//          this.userMongoService.create(user);
//          return ServerResponse.ok().body(BodyInserters.fromObject(user));
//        });

    Mono<User> userMono = request.bodyToMono(User.class);
    return ServerResponse.ok().build(this.userMongoService.insert(userMono).then());
  }

  public Mono<ServerResponse> update(ServerRequest request) {
    return request.body(BodyExtractors.toMono(User.class))
        .flatMap(user -> {
          this.userMongoService.update(user);
          return ServerResponse.ok().body(BodyInserters.fromObject(user));
        });
  }

  public Mono<ServerResponse> delete(ServerRequest request) {
    return ServerResponse.ok()
        .body(this.userMongoService.delete(request.pathVariable("id")), Boolean.class);
  }
}
