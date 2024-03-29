package com.websocket.websocketchat.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.websocketchat.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber /*implements MessageListener*/ {
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    public void sendMessage(String publishMessage) {
        try {
            // ChatMessage 객채로 맵핑
            ChatMessage chatMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
            // 채팅방을 구독한 클라이언트에게 메시지 발송
            messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
        } catch (Exception e) {
            log.error("Exception {}", e);
        }
    }



//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        try {
//            //redis에서 발행된 데이터를 받아 deserialize
//            String publishMessage=(String) redisTemplate.getStringSerializer().deserialize(message.getBody());
//            //ChatMessage 객체로 맵핑
//            ChatMessage roomMessage=objectMapper.readValue(publishMessage, ChatMessage.class);
//            //Websocket 구독자에게 채팅메시지 Send
//            messagingTemplate.convertAndSend("/sub/chat/room/"+roomMessage.getRoomId(), roomMessage);
//        } catch (Exception e) {
//           log.error(e.getMessage());
//        }
//    }
}
