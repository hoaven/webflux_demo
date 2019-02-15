package edu.xinan.demo.config;

import edu.xinan.demo.handler.CalculatorHandler;
import edu.xinan.demo.handler.UserMongoHandler;
import edu.xinan.demo.handler.UserRedisHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * Created by hoaven on 2019/2/14
 */
@Configuration
public class RouteConfig {

  /**
   * RouterFunction 为 HandlerFunction 提供路由信息
   * @param calculatorHandler
   * @return
   */
  //@Bean
  public RouterFunction<ServerResponse> routerFunction(final CalculatorHandler calculatorHandler) {
    /**
     * 接口 RouterFunction 的方法 Mono<HandlerFunction<T extends ServerResponse>> route(ServerRequest request)对每个 ServerRequest，
     * 都返回对应的 0 个或 1 个 HandlerFunction 对象，以 Mono<HandlerFunction>来表示。
     * 当找到对应的 HandlerFunction 时，该 HandlerFunction 被调用来处理该 ServerRequest，并把得到的 ServerResponse 返回。
     * 方法 RouterFunctions.route 用来根据 Predicate 是否匹配来确定 HandlerFunction 是否被应用。
     */
    return RouterFunctions.route(RequestPredicates.path("/calculator"), request ->
        // queryParam 方法来获取到查询参数 operator 的值 , 只能获取GET请求参数
        request.queryParam("operator").map(operator ->
            // 通过反射 API 在类 CalculatorHandler 中找到与查询参数 operator 的值名称相同的方法来确定要调用的 HandlerFunction 的实现
            Mono.justOrEmpty(ReflectionUtils.findMethod(CalculatorHandler.class, operator, ServerRequest.class))
                // 最后调用查找到的方法来处理该请求。
                .flatMap(method -> (Mono<ServerResponse>) ReflectionUtils.invokeMethod(method, calculatorHandler, request))
                // 如果找不到查询参数 operator 或是 operator 的值不在识别的列表中，服务器端返回 400 错误
                .switchIfEmpty(ServerResponse.badRequest().build())
                // 如果反射 API 的方法调用中出现错误，服务器端返回 500 错误
                .onErrorResume(ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build()))
            .orElse(ServerResponse.badRequest().build()));
  }

  @Bean
  public RouterFunction<ServerResponse> routerFunction(final UserMongoHandler userMongoHandler, final UserRedisHandler userRedisHandler) {
    return RouterFunctions.route(GET("/user/mongo/list"), userMongoHandler::list)
        // 默认参数为application/json
        .andRoute(POST("/user/mongo").and(contentType(APPLICATION_JSON)), userMongoHandler::create)
        .andRoute(GET("/user/mongo/single/{id}"), userMongoHandler::getById)
        .andRoute(PUT("/user/mongo"), userMongoHandler::update)
        .andRoute(DELETE("/user/mongo/{id}"), userMongoHandler::delete)

        // redis
        .andRoute(GET("/user/redis/list"), userRedisHandler::list)
        .andRoute(POST("/user/redis"), userRedisHandler::create)
        .andRoute(GET("/user/redis/single/{id}"), userRedisHandler::getById)
        .andRoute(PUT("/user/redis"), userRedisHandler::update)
        .andRoute(DELETE("/user/redis/{id}"), userRedisHandler::delete);
  }
}
