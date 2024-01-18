package com.websocket.websocketchat.model;

import com.websocket.websocketchat.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom implements Serializable {
    private static final long serialVersionUID = 6494678977089006639L;
    private String roomId;
    private String name;
    private long userCount; // 채팅방 인원수

    //pub/sub방식 이용하면 구독자 관리가 알아서 되므로 웹소켓 세션 관리가 필요 없어짐
    //발송 구현도 알아서 되므로 일일이 클라이언트에게 메시지를 발송하는 구현이 필요 없어 진다.
    //그러므로 dto가 간소화 된다
//    private Set<WebSocketSession> sessions=new HashSet<>();

    @Builder
    public ChatRoom(String name) {
//        ChatRoom chatRoom=new ChatRoom();
        this.roomId= UUID.randomUUID().toString();
        this.name=name;
    }

//    public void handlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService){
//        //잠시 기둘
//        if(chatMessage.getType().equals(ChatMessage.MessageType.ENTER)){
//            sessions.add(session);
//            chatMessage.setMessage(chatMessage.getSender()+"님 입장 했슘");
//        }
//        sendMessage(chatMessage, chatService);
//    }
//
//    public <T> void sendMessage(T message, ChatService chatService){
//        sessions.parallelStream().forEach(session->chatService.sendMessage(session,message));
//    }
}
