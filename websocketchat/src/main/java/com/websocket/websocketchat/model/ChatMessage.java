package com.websocket.websocketchat.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    public ChatMessage(){
    }
    @Builder
    public ChatMessage(MessageType type, String roomId, String sender, String message, long userCount){
        this.type=type;
        this.roomId=roomId;
        this.sender=sender;
        this.message=message;
        this.userCount=userCount;
    }

    public enum MessageType{
        ENTER, QUIT, TALK
    }
    private MessageType type;//메세지 타입
    private String roomId;//방번호
    private String sender;//메시지 보낸사람
    private String message;//메시지
    private  long userCount;//채팅방 인원수



}
