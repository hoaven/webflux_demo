package edu.xinan.demo.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.function.BiFunction;

/**
 * 在函数式编程模型中，每个请求是由一个函数来处理的， 通过接口 HandlerFunction 来表示;
 * HandlerFunction 是一个函数式接口，其中只有一个方法 Mono<T extends ServerResponse> handle(ServerRequest request)，因此可以用 labmda 表达式来实现该接口;
 * 接口 ServerRequest 表示的是一个 HTTP 请求。通过该接口可以获取到请求的相关信息，如请求路径、HTTP 头、查询参数和请求内容等;
 * 方法 handle 的返回值是一个 Mono<T extends ServerResponse>对象。接口 ServerResponse 用来表示 HTTP 响应。ServerResponse 中包含了很多静态方法来创建不同 HTTP 状态码的响应对象;
 *
 * Created by hoaven on 2019/2/14
 */
@Component
public class CalculatorHandler {

  public Mono<ServerResponse> add(final ServerRequest request) {
    return calculate(request, (v1, v2) -> v1 + v2);
  }

  public Mono<ServerResponse> subtract(final ServerRequest request) {
    return calculate(request, (v1, v2) -> v1 - v2);
  }

  public Mono<ServerResponse> multiply(final ServerRequest request) {
    return calculate(request, (v1, v2) -> v1 * v2);
  }

  public Mono<ServerResponse> divide(final ServerRequest request) {
    return calculate(request, (v1, v2) -> v1 / v2);
  }

  private Mono<ServerResponse> calculate(final ServerRequest request,
      final BiFunction<Integer, Integer, Integer> calculateFunc) {
    final Tuple2<Integer, Integer> operands = extractOperands(request);
    return ServerResponse
        .ok()
        .body(Mono.just(calculateFunc.apply(operands.getT1(), operands.getT2())), Integer.class);
  }

  /**
   * 从 ServerRequest 中取得两个操作数参数 并转换成二元组
   * @param request
   * @return
   */
  private Tuple2<Integer, Integer> extractOperands(final ServerRequest request) {
    return Tuples.of(parseOperand(request, "v1"), parseOperand(request, "v2"));
  }

  private int parseOperand(final ServerRequest request, final String param) {
    try {
      return Integer.parseInt(request.queryParam(param).orElse("0"));
    } catch (final NumberFormatException e) {
      return 0;
    }
  }
}
