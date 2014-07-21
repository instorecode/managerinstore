package br.com.instore.web.websocket;

import java.io.IOException;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/ws/limpar-cache")
public class CacheEndPoint {

    @OnMessage
    public void message(String message, Session client) throws IOException, EncodeException {
        System.out.println("message: " + message);
        for (Session peer : client.getOpenSessions()) {
            peer.getBasicRemote().sendText(message);
        }
    }
}
