package com.websocket.websocketchat.controller;

import com.websocket.websocketchat.model.ChatMessage;
import com.websocket.websocketchat.model.ChatRoom;
//import com.websocket.websocketchat.service.ChatService;
import com.websocket.websocketchat.pubsub.RedisPublisher;
import com.websocket.websocketchat.repo.ChatRoomRepository;
import com.websocket.websocketchat.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.core.AbstractDestinationResolvingMessagingTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;
//    private final RedisPublisher redisPublisher;
    private final ChannelTopic channelTopic;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    @MessageMapping("/chat/message") // websocket으로 들어오는 메세지 발행을 처리한다.
    public void message(ChatMessage message, @Header("token") String token) {
        String nickname = jwtTokenProvider.getUserNameFromJwt(token);
        // 로그인 회원 정보로 대화명 설정
        message.setSender(nickname);

        log.info("발신 message : {}", message);

        // 채팅방 입장시에는 대화명과 메시지를 자동으로 세팅한다.
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setSender("[알림]");
//            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(nickname + "님이 입장하셨습니다.");
        }
        // WebSocket에 발행된 메세지를 redis로 발행(publish)
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }

//
//    @MessageMapping("/chat/message")
//    public void message(ChatMessage message, @Header("token") String token) {
//        String nickname = jwtTokenProvider.getUserNameFromJwt(token);
//        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
//            chatRoomRepository.enterChatRoom(message.getRoomId());
//            message.setMessage(nickname + "님이 입장하셨습니다.");
//        }
//        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
//        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
//    }

//    @MessageMapping("/chat/message")
//    public void message(ChatMessage message){
//        if(ChatMessage.MessageType.ENTER.equals(message.getType())){
//            chatRoomRepository.enterChatRoom(message.getRoomId());
//            message.setMessage(message.getSender()+"님 입장하셨습니다람쥐");
//        }
////        messagingTemplate.convertAndSend("/sub/chat/room/"+message.getRoomId(), message);
//        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
//        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
//    }
//
//    private final ChatService chatService;
//
//    @PostMapping
//    public ChatRoom createRoom(@RequestParam String name) {
//        return chatService.createRoom(name);
//    }
//
//    @GetMapping
//    public List<ChatRoom> findAllRoom() {
//        return chatService.findAllRoom();
//    }
}