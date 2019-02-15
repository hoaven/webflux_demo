package edu.xinan.demo.service;

import edu.xinan.demo.dao.UserRedisRepository;
import edu.xinan.demo.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * Created by hoaven on 2019/2/14
 */
@Slf4j
@Service
public class UserRedisService implements UserService {

  @Resource
  UserRedisRepository userRedisRepository;

  @Override
  public Flux<User> list() {
    return userRedisRepository.findAll();
  }

  @Override
  public Flux<User> getById(Flux<String> ids) {
    return ids.flatMap(id -> userRedisRepository.findById(id));
  }

  @Override
  public Mono<User> getById(String id) {
    return userRedisRepository.findById(id);
  }

  @Override
  public Mono<User> create(User user) {
    return userRedisRepository.save(user);
  }

  @Override
  public Flux<User> insert(Mono<User> userMono) {
    return null;
  }

  @Override
  public Mono<User> update(User user) {
    return userRedisRepository.update(user);
  }

  @Override
  public Mono<Boolean> delete(String id) {
    return userRedisRepository.deleteById(id);
  }
}
