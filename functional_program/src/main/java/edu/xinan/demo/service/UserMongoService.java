package edu.xinan.demo.service;

import edu.xinan.demo.dao.UserMongoRepository;
import edu.xinan.demo.po.User;
import edu.xinan.demo.util.BeanConvertUtils;
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
public class UserMongoService implements UserService {

  @Resource
  UserMongoRepository userMongoRepository;

  @Override
  public Flux<User> list() {
    return userMongoRepository.findAll();
  }

  @Override
  public Flux<User> getById(Flux<String> ids) {
    return ids.flatMap(id -> userMongoRepository.findById(id));
  }

  @Override
  public Mono<User> getById(String id) {
    return userMongoRepository.findById(id);
  }

  @Override
  public Mono<User> create(User user) {
    return userMongoRepository.save(user);
  }

  @Override
  public Flux<User> insert(Mono<User> userMono) {
    return userMongoRepository.insert(userMono);
  }

  @Override
  public Mono<User> update(User user) {
    return userMongoRepository.findById(user.getId())
        .map(dbUser -> {
          dbUser = BeanConvertUtils.map(user, User.class);
          return dbUser;
        })
        .flatMap(this::create);
  }

  @Override
  public Mono<Boolean> delete(String id) {
    userMongoRepository.deleteById(id);
    return Mono.just(true);
  }
}
