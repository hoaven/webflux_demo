package edu.xinan.demo.controller;

import edu.xinan.demo.po.User;
import edu.xinan.demo.api.UserMongoServerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created by hoaven on 2019/2/14
 */
@Slf4j
@RestController
@RequestMapping("/user/mongo")
public class UserMongoController {

  @Autowired
  private UserMongoServerClient userMongoServerClient;

  @GetMapping("/hello/test")
  public Object test() {
    log.info("/user/reactive/test request begin");
    return "success";
  }

  @GetMapping("/list")
  public List<User> list() {
    return this.userMongoServerClient.list();
  }

  @GetMapping("/single/{id}")
  public User getById(@PathVariable("id") final String id) {
    return this.userMongoServerClient.getById(id);
  }

  @PostMapping("")
  public User create(@RequestBody final User user) {
    return this.userMongoServerClient.create(user);
  }

  @PutMapping("/{id}")
  public User update(@PathVariable("id") final String id, @RequestBody final User user) {
    return this.userMongoServerClient.update(id, user);
  }

  @DeleteMapping("/{id}")
  public Boolean delete(@PathVariable("id") final String id) {
    return this.userMongoServerClient.delete(id);
  }
}
