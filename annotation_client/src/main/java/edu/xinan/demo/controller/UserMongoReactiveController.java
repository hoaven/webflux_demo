package edu.xinan.demo.controller;

import edu.xinan.demo.po.User;
import edu.xinan.demo.service.UserMongoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by hoaven on 2019/2/14
 */
@Slf4j
@RestController
@RequestMapping("/user/reactive")
public class UserMongoReactiveController {

  @Autowired
  private UserMongoService userMongoService;

  @GetMapping("/list")
  public Flux<User> list() {
    log.info("/user/reactive/list request begin");
    return this.userMongoService.list();
  }

  @GetMapping("/single/{id}")
  public Mono<User> getById(@PathVariable("id") final String id) {
    return this.userMongoService.getById(id);
  }

  @PostMapping("")
  public Mono<User> create(@RequestBody final User user) {
    return this.userMongoService.create(user);
  }
//
//  @PutMapping("/{id}")
//  public User update(@PathVariable("id") final String id, @RequestBody final User user) {
//    return this.userMongoService.update(id, user);
//  }
//
//  @DeleteMapping("/{id}")
//  public Boolean delete(@PathVariable("id") final String id) {
//    return this.userMongoService.delete(id);
//  }
}
