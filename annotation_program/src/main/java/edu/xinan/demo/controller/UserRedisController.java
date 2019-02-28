package edu.xinan.demo.controller;

import edu.xinan.demo.po.User;
import edu.xinan.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Created by hoaven on 2019/2/14
 */
@RestController
@RequestMapping("/user/redis")
public class UserRedisController {

  @Autowired
  private UserService userRedisService;

  @GetMapping("/list")
  public Flux<User> list() {
    return this.userRedisService.list();
  }

  @GetMapping("/single/{id}")
  public Mono<User> getById(@PathVariable("id") final String id) {
    return this.userRedisService.getById(id);
  }

  @PostMapping("")
  public Mono<User> create(@RequestBody final User user) {
    return this.userRedisService.create(user);
  }

  @PutMapping("/{id}")
  public Mono<User> update(@PathVariable("id") final String id, @RequestBody final User user) {
    Objects.requireNonNull(user);
    user.setId(id);
    return this.userRedisService.update(user);
  }

  @DeleteMapping("/{id}")
  public Mono<Boolean> delete(@PathVariable("id") final String id) {
    return this.userRedisService.delete(id);
  }
}
