package edu.xinan.demo.config;

import edu.xinan.demo.po.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by hoaven on 2019/2/14
 */
@Configuration
public class RedisConfiguration {

  /**
   * 注册 ReactiveRedisTemplate
   * @param connectionFactory
   * @return
   */
  @Bean
  public ReactiveRedisTemplate<String, User> reactiveJsonPostRedisTemplate(
      ReactiveRedisConnectionFactory connectionFactory) {
    RedisSerializationContext<String, User> serializationContext = RedisSerializationContext
        .<String, User>newSerializationContext(new StringRedisSerializer())
        .hashKey(new StringRedisSerializer())
        .hashValue(new Jackson2JsonRedisSerializer<>(User.class))
        .build();
    return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
  }
}
