package com.mall.service;

import com.mall.dto.NotifyDTO;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/19
 */

@Slf4j
@ServerEndpoint("/webSocket/{userId}")
@Component
public class WebSocketServer {
    private final static ConcurrentHashMap<Long, Session> sessionMap = new ConcurrentHashMap<>();
    private Long userId;

    /**
     * 尝试建立连接时的回调
     *
     * @param userId 用户id
     * @param session Session对象
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/19 21:17
     */
    @OnOpen
    public void onOpen(@PathParam("userId") Long userId, Session session) {
        log.info("用户:{}已连接", userId);
        this.userId = userId;
        sessionMap.put(userId, session);
    }

    /**
     * 连接在关闭时的回调
     *
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/19 21:21
     */
    @OnClose
    public void onClose() {
        if (sessionMap.containsKey(userId)) {
            sessionMap.remove(userId);
        }
    }


    /**
     * 接收到客户端消息之后的回调
     *
     * @param message
     * @param userId
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/19 21:29
     */
    @OnMessage
    public void onReceiveMessage(String message, @PathParam("userId") Long userId) {
        System.out.println("收到来自用户 " + userId + " 的消息: " + message);
    }

    /**
     * 发生错误时的回调
     *
     * @param error 异常
     */
    @OnError
    public void onError(Throwable error) {
        log.error("发生错误: {}", error.getMessage());
    }

    /**
     * 发送消息给指定用户
     *
     * @param notifyDTO 消息传输对象
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/19 21:46
     */
    public static void sendMessage(NotifyDTO notifyDTO) throws IOException {
        Long userId = notifyDTO.getToUserId();
        if (isSendToSpecifiedUser(userId)) {
            Session session = sessionMap.get(userId);
            checkIfSendAMessage(session);
            session.getBasicRemote().sendText(notifyDTO.getContent());
        } else {
            for (Session userSession : sessionMap.values()) {
                userSession.getBasicRemote().sendText(notifyDTO.getContent());
            }
        }
   }

    private static boolean isSendToSpecifiedUser(Long userId) {
        return userId != null;
    }

    private static void checkIfSendAMessage(Session session) {
        if (session == null)
            throw new RuntimeException("session尚未初始化");

        if (!session.isOpen())
            throw new RuntimeException("与客户端连接已关闭");
    }
}
