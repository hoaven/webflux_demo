package edu.xinan.demo.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * WebSocket 支持客户端与服务器端的双向通讯。当客户端与服务器端之间的交互方式比较复杂时，可以使用 WebSocket
 * WebSocketHandler 用来处理 WebSocket 通讯
 * Created by hoaven on 2019/2/14
 */
@Component
public class WebSocketSseHandler implements WebSocketHandler {

  /**
   * WebSocketSession 可以用来获取客户端信息、接送消息和发送消息。
   * 在 handle 方法，使用 map 操作对 receive 方法得到的 Flux<WebSocketMessage>中包含的消息继续处理，然后直接由 send 方法来发送
   * @param session
   * @return
   */
  @Override
  public Mono<Void> handle(WebSocketSession session) {
    return session.send(
        session.receive()
            .map(msg -> session.textMessage("WS SERVICE ECHO -> " + msg.getPayloadAsText())));
  }

  /**
   * 测试：
   * 1. 运行应用
   * 2. 使用工具：https://www.websocket.org/echo.html
   * 3. 连接到 ws://localhost:8080/echo
   * 4. 发送消息并查看服务器端返回的结果
   */
}
