package com.app.home.messenger;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.app.home.user.UserVO;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.endpoints.internal.Substring;

@Component
@Slf4j
public class SocketHandler extends TextWebSocketHandler{

	
	private static List<WebSocketSession> weblist = new ArrayList<>();
	
	HashMap<String, WebSocketSession>sessionMap=new HashMap<>(); // 웹소켓 세션을 담아둘 맵
	

	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
	//메서지 발송
		String msg= message.getPayload();
		for (String key: sessionMap.keySet()) {
			WebSocketSession wss= sessionMap.get(key);
			try {
				System.out.println("handler - message : "+ msg);
			wss.sendMessage(new TextMessage(msg));
			}catch(Exception e) {
				e.printStackTrace();
			
			}
     	}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		SecurityContextImpl contextImpl= (SecurityContextImpl)session.getAttributes().get("SPRING_SECURITY_CONTEXT");
		UserVO userVO = (UserVO)contextImpl.getAuthentication().getPrincipal();
		
		// 웹소켓 주소
		// ws://localhost:81/chatroom/숫자 <- 숫자만 가져오기
		
		String uri = session.getUri().toString();
		uri=uri.substring(uri.lastIndexOf("/")+1);
		
		
		String message = "{\"type\":\"connect\",\"username\":\""+userVO.getName()+"\",\"roomNum\":\""+uri+"\"}";
		sessionMap.put(session.getId(), session);
		
		for (String key: sessionMap.keySet()) {
			WebSocketSession wss= sessionMap.get(key);
			try {	
			wss.sendMessage(new TextMessage(message));//userVO.getName()));
			System.out.println("after : "+message);
			
			}catch(Exception e) {
				e.printStackTrace();
			}
     	}
		
		weblist.add(session);
		log.info(session + "접속");
	
			
		}
		 

	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
	
	SecurityContextImpl contextImpl= (SecurityContextImpl)session.getAttributes().get("SPRING_SECURITY_CONTEXT");
	UserVO userVO = (UserVO)contextImpl.getAuthentication().getPrincipal();
	
	String uri = session.getUri().toString();
	uri=uri.substring(uri.lastIndexOf("/")+1);

	String message = "{\"type\":\"disconnect\",\"username\":\""+userVO.getName()+"\",\"roomNum\":\""+uri+"\"}";
	
	// 소켓 종료	
	sessionMap.remove(session.getId());
	//super.afterConnectionClosed(session, status);
	
	log.info(session + "접속 해제");
	weblist.remove(session);
	for (String key: sessionMap.keySet()) {
		WebSocketSession wss= sessionMap.get(key);
		try {
			
		wss.sendMessage(new TextMessage(message));//userVO.getName()));
		}catch(Exception e) {
			e.printStackTrace();
		}
 	}
	
	}
	
}