package com.appserver.logic.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value = "/logic")
public class LoginEndpoint {

	private static Set<Session> onlineSessions = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnMessage
    public String onMessage(String message, Session session) {
		System.out.println(message);
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return message;
    }
	
	@OnOpen
    public void onOpen (Session session) {
		onlineSessions.add(session);
		System.out.println("session onOpen");
		try {
			session.getBasicRemote().sendText("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @OnClose
    public void onClose (Session session, CloseReason closeReason) {
    	onlineSessions.remove(session);
    	System.out.println("session onClose");
    }
	
    @OnError
    public void onError(Session session, Throwable exception) {
        
    }
}
