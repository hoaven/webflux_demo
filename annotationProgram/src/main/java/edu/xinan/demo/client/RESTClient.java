package edu.xinan.demo.client;

import edu.xinan.demo.po.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Created by hoaven on 2019/2/18
 */
public class RESTClient {
  public static void main(final String[] args) {
    testSingle();
  }

  private static void testCreate() {
    final User user = new User();
    user.setName("Client");
    user.setEmail("client@example.org");
    WebClient client = WebClient.create("http://localhost:8888/user/mongo");
    Mono<User> createdUser = client.post()
        .uri("")
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(user), User.class)
        .exchange()
        .flatMap(response -> response.bodyToMono(User.class));
    System.out.println(createdUser.block());
  }

  private static void testSingle() {
    WebClient client = WebClient.create("http://localhost:8888/user/mongo/single");
    Mono<User> singleUser = client.get()
        .uri("/5c6a1bc6104b79341cda3c0c")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .flatMap(response -> response.bodyToMono(User.class));

    // block 作用是等待请求完成并得到所产生的类 User 的对象
    System.out.println(singleUser.block());
  }
}
