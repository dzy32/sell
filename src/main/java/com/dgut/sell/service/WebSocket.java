package com.dgut.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author YS
 * @data 2020/2/7 22:13
 */
@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

    private Session session;

    private  static CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSockets.add(this);
        log.info("【websocket 消息】 有新的连接 ， 总数 {}",webSockets.size());
    }

    @OnClose
    public void onClose(){
        webSockets.remove(this);
        log.info("【websocket 消息】 断开连接 ， 总数 {}",webSockets.size());
    }

    @OnMessage
    public void onMessage(String message){
        log.info("【websocket 消息】 收到客户端的信息：{}",message);
    }

    public void sendMessage(String message){
        for(WebSocket webSocket:webSockets){
            log.info("【websock消息】 广播信息：message = {}",message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


}
