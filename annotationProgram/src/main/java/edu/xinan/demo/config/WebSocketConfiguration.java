package edu.xinan.demo.config;

import edu.xinan.demo.handler.WebSocketSseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * 将 webSocketSseHandler 注册到 WebFlux 中
 * Created by hoaven on 2019/2/14
 */
@Configuration
public class WebSocketConfiguration {
  @Bean
  public HandlerMapping webSocketMapping(final WebSocketSseHandler webSocketSseHandler) {
    final Map<String, WebSocketHandler> map = new HashMap<>(1);
    map.put("/echo", webSocketSseHandler);

    final SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
    mapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
    mapping.setUrlMap(map);
    return mapping;
  }

  /**
   * WebSocketHandlerAdapter 对象负责把 WebSocketHandler 关联到 WebFlux 中
   * @return
   */
  @Bean
  public WebSocketHandlerAdapter handlerAdapter() {
    return new WebSocketHandlerAdapter();
  }
}
