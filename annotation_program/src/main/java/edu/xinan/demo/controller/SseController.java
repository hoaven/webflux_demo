package edu.xinan.demo.controller;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 服务器推送事件（Server-Sent Events，SSE）允许服务器端不断地推送数据到客户端
 * Created by hoaven on 2019/2/14
 */
@RestController
@RequestMapping("/sse")
public class SseController {

  /**
   * WebFlux 中创建 SSE 的服务器端:只需要返回的对象的类型是 Flux<ServerSentEvent>，
   * 就会被自动按照 SSE 规范要求的格式来发送响应。
   * 相对于 WebSocket 而言，服务器推送事件只支持服务器端到客户端的单向数据传递。
   * 此方法是每隔一秒产生一个随机数的 SSE 端点
   * @return
   */
  @GetMapping("/random_numbers")
  public Flux<ServerSentEvent<Integer>> randomNumbers() {
    // interval 创建一个包含了从 0 开始递增的 Long 对象的序列。其中包含的元素按照指定的间隔来发布。
    return Flux.interval(Duration.ofSeconds(1))
        // 元素转换,seq表示上述从 0 开始递增的 Long 对象的序列的元素，即将单个元素转换成元组（该元组含两个元素）
        .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
        // 创建ServerSentEvent
        .map(data -> ServerSentEvent.<Integer>builder()
            // 事件名称
            .event("random")
            // 事件标识
            .id(Long.toString(data.getT1()))
            // 事件数据
            .data(data.getT2())
            .build())
        // 添加日志 - 方便查看一直在创建随机数
        .log("Create random numbers");
  }
}
