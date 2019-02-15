package edu.xinan.demo.controller;

import edu.xinan.demo.exception.ResourceNotFoundException;
import edu.xinan.demo.po.User;
import edu.xinan.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Created by hoaven on 2019/2/14
 */
@RestController
@RequestMapping("/user/static/data")
public class UserStaticDataController {
  @Autowired
  private UserService userStaticDataService;

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
  @ExceptionHandler(ResourceNotFoundException.class)
  public void notFound() {
  }

  @GetMapping("/list")
  public Flux<User> list() {
    return this.userStaticDataService.list();
  }

  @GetMapping("/single/{id}")
  public Mono<User> getById(@PathVariable("id") final String id) {
    return this.userStaticDataService.getById(id);
  }

  @PostMapping("")
  public Mono<User> create(@RequestBody final User user) {
    return this.userStaticDataService.create(user);
  }

  @PutMapping("/{id}")
  public Mono<User>  update(@PathVariable("id") final String id, @RequestBody final User user) {
    Objects.requireNonNull(user);
    user.setId(id);
    return this.userStaticDataService.update(user);
  }

  @DeleteMapping("/{id}")
  public Mono<Boolean>  delete(@PathVariable("id") final String id) {
    return this.userStaticDataService.delete(id);
  }
}
