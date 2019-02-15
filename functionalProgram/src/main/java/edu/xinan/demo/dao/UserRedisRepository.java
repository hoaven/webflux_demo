package edu.xinan.demo.dao;

import edu.xinan.demo.po.User;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by hoaven on 2019/2/14
 */
@Component
public class UserRedisRepository {

  @Resource
  ReactiveRedisOperations<String, User> template;

  public Flux<User> findAll() {
    return template.<String, User>opsForHash().values("users");
  }

  public Mono<User> findById(String id) {
    return template.<String, User>opsForHash().get("users", id);
  }

  public Mono<User> save(User user) {
    if (user.getId() == null) {
      String id = UUID.randomUUID().toString();
      user.setId(id);
    }
    return template.<String, User>opsForHash().put("users", user.getId(), user)
        .log()
        .map(p -> user);
  }

  public Mono<User> update(User user) {
    return template.<String, User>opsForHash().put("users", user.getId(), user)
        .log()
        .map(p -> user);
  }

  public Mono<Boolean> deleteById(String id) {
    template.<String, User>opsForHash().remove("users", id);
    return Mono.just(true);
  }

  public Mono<Boolean> deleteAll() {
    return template.<String, User>opsForHash().delete("users");
  }
}
