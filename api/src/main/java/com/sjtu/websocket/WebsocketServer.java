package com.sjtu.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{oid}")
public class WebsocketServer {

    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap();

    /**前端发送请求建⽴websocket连接，就会执⾏@OnOpen⽅法**/
    @OnOpen
    public void open(@PathParam("oid") String orderId,Session session){
        System.out.println("---websocket连接建立" + orderId);
        sessionMap.put(orderId,session);
    }

    /**前端关闭页面或者主动关闭websocket连接，就会执⾏@OnClose⽅法**/
    @OnClose
    public void close(@PathParam("oid") String orderId){
        sessionMap.remove(orderId);
    }

    //封装一个方法，调用后向前端连接发生消息
    public void sendMsg(String orderId,String msg){
        try {
            Session session = sessionMap.get(orderId);
            session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
