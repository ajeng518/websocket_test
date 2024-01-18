package com.websocket.websocketchat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.websocketchat.model.ChatMessage;
import com.websocket.websocketchat.model.ChatRoom;
import com.websocket.websocketchat.repo.ChatRoomRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final ChatRoomRepository chatRoomRepository;

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms;

    /**
     * destination정보에서 roomId 추출
     */
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }
    /**
     * 채팅방에 메시지 발송
     */
    public void sendChatMessage(ChatMessage chatMessage) {
        chatMessage.setUserCount(chatRoomRepository.getUserCount(chatMessage.getRoomId()));
        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 방에 입장했습니다.");
            chatMessage.setSender("[알림]");
        } else if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 방에서 나갔습니다.");
            chatMessage.setSender("[알림]");
        }
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }


//    @PostConstruct
//    private void init(){
//        chatRooms=new LinkedHashMap<>();
//    }
//    public List<ChatRoom> findAllRoom(){
//        return new ArrayList<>(chatRooms.values());
//    }
//
//    public ChatRoom findRoomById(String roomId){
//        return chatRooms.get(roomId);
//    }
//    public ChatRoom createRoom(String name){
//        String randomId=UUID.randomUUID().toString();
//        ChatRoom chatRoom=ChatRoom.builder()
////                .roomId(randomId)
//                .name(name)
//                .build();
//        chatRooms.put(randomId, chatRoom);
//        return chatRoom;
//    }
//
//    public <T> void sendMessage(WebSocketSession session, T message){
//        try{
//            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
//        }catch(IOException e){
//            log.error(e.getMessage(), e);
//        }
//    }
}
