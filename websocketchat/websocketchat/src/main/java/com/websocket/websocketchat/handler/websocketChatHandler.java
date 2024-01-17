package com.websocket.websocketchat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.websocketchat.model.ChatMessage;
import com.websocket.websocketchat.model.ChatRoom;
import com.websocket.websocketchat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
public class websocketChatHandler {//extends TextWebSocketHandler {
//    private final ObjectMapper objectMapper;
//    private final ChatService chatService;
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
//        String payload = message.getPayload();
//        log.info("payload {}", payload); // log 확인
////        TextMessage textMessage = new TextMessage("welcome chat ^^bb");
////        session.sendMessage(textMessage);
//        try {
//            ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
//            ChatRoom room=chatService.findRoomById(chatMessage.getRoomId());
//            room.handlerActions(session, chatMessage, chatService);
//        }catch(Exception e){
//            log.error(e.getMessage());
//        }
//
//
//    }
}
