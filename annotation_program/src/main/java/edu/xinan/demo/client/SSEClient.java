package edu.xinan.demo.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

/**
 * Created by hoaven on 2019/2/18
 */
public class SSEClient {

  public static void main(String[] args) {
    WebClient client = WebClient.create();
    client.get()
        .uri("http://localhost:8888/sse/random_numbers")
        .accept(MediaType.TEXT_EVENT_STREAM)
        .exchange()
        // flatMapMany 将【异步消息流】中的 Mono<ServerResponse>转换成一个 Flux<ServerSentEvent>对象
        // ParameterizedTypeReference<ServerSentEvent<String>>() {} 表明了响应消息流中的内容是 ServerSentEvent 对象
        .flatMapMany(response -> response.body(BodyExtractors.toFlux(new ParameterizedTypeReference<ServerSentEvent<String>>() {})))
        .filter(sse -> Objects.nonNull(sse.data()))
        .map(ServerSentEvent::data)
        // 由于 SSE 服务器会不断地发送消息，此处取前10条
        .buffer(10)
        .doOnNext(System.out::println)
        .blockFirst();
  }
}
