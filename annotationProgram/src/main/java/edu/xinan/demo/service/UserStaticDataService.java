package edu.xinan.demo.service;

import edu.xinan.demo.exception.ResourceNotFoundException;
import edu.xinan.demo.po.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类 UserService 中的方法都以 Flux 或 Mono 对象作为返回值，这也是 WebFlux 应用的特征
 * Created by hoaven on 2019/2/14
 */
@Service
public class UserStaticDataService implements UserService{

  private final Map<String, User> data = new ConcurrentHashMap<>();

  @Override
  public Flux<User> list() {
    // fromIterable 从序列中创建Flux对象
    return Flux.fromIterable(this.data.values());
  }

  @Override
  public Flux<User> getById(final Flux<String> ids) {
    // flatMap 操作符把流中的每个元素转换成一个流，再把所有流中的元素进行合并；而 map 操作符是纯元素转换
    return ids.flatMap(id -> Mono.justOrEmpty(this.data.get(id)));
  }

  @Override
  public Mono<User> getById(final String id) {
    return Mono.justOrEmpty(this.data.get(id))
        // 为空则抛出异常
        .switchIfEmpty(Mono.error(new ResourceNotFoundException()));
  }

  @Override
  public Mono<User> create(User user) {
    this.data.put(user.getId(), user);
    return Mono.just(user);
  }

  @Override
  public Mono<User> update(User user) {
    this.data.put(user.getId(), user);
    return Mono.just(user);
  }

  @Override
  public Mono<Boolean> delete(final String id) {
    // justOrEmpty 从一个 Optional 对象或可能为 null 的对象中创建 Mono
    this.data.remove(id);
    return Mono.just(true);
  }
}
