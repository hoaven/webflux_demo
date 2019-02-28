package edu.xinan.demo.api;

import edu.xinan.demo.po.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Created by hoaven on 2019/2/20
 */
@FeignClient("annotation-program")
public interface UserMongoServerClient {

  @GetMapping("/user/mongo/list")
  List<User> list();

  @GetMapping("/user/mongo/single/{id}")
  User getById(@PathVariable("id") final String id);

  @PostMapping("/user/mongo")
  User create(@RequestBody final User user);

  @PutMapping("/user/mongo/{id}")
  User update(@PathVariable("id") final String id, @RequestBody final User user);

  @DeleteMapping("/user/mongo/{id}")
  Boolean delete(@PathVariable("id") final String id);

}
