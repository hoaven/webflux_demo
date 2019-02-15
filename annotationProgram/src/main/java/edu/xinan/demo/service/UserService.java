package edu.xinan.demo.service;

import edu.xinan.demo.po.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by hoaven on 2019/2/14
 */
public interface UserService {

  public Flux<User> list();

  public Flux<User> getById(final Flux<String> ids);

  public Mono<User> getById(final String id);

  public Mono<User> create(final User user);

  public Mono<User> update(final User user);

  public Mono<Boolean> delete(final String id);
}
