package com.websocket.websocketchat.repo;

import com.websocket.websocketchat.model.ChatRoom;
import com.websocket.websocketchat.pubsub.RedisSubscriber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ChatRoomRepository {
    //채팅방(topic)에 발행되는 메시지를 처리할 Listner
    private final RedisMessageListenerContainer redisMessageListener;
    //구독 처리 서비스
//    private final RedisSubscriber redisSubscriber;
    //Redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
    }
    // 모든 채팅방 조회
    public List<ChatRoom> findAllRoom() {
        return opsHashChatRoom.values(CHAT_ROOMS);
    }
    // 특정 채팅방 조회
    public ChatRoom findRoomById(String id) {
        return opsHashChatRoom.get(CHAT_ROOMS, id);
    }
    // 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.builder().name(name).build();
        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
//    private static final String CHAT_ROOMS="CHAT_ROOM";
//    private final RedisTemplate<String, Object>redisTemplate;
//    private HashOperations<String, String, ChatRoom>opsHashChatRoom;
    //채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서서별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
//    private Map<String, ChannelTopic>topics;

//    private Map<String, ChatRoom> chatRoomMap;


//    @PostConstruct
//    private void init() {
////        chatRoomMap = new LinkedHashMap<>();
//        opsHashChatRoom = redisTemplate.opsForHash();
//        topics = new HashMap<>();
//    }
//
//    public List<ChatRoom> findAllRoom(){
////        //채팅방 생성순서 최근순으로 반환
////        List chatRooms=new ArrayList<>(chatRoomMap.values());
////        Collections.reverse(chatRooms);
////        return chatRooms;
//        return opsHashChatRoom.values(CHAT_ROOMS);
//    }
//
//    public ChatRoom findRoomById(String id){
////        return chatRoomMap.get(id);
//        return opsHashChatRoom.get(CHAT_ROOMS, id);
//    }
//
//    public ChatRoom createChatRoom(String name){
//        ChatRoom chatRoom=ChatRoom.builder()
//                .name(name)
//                .build();
////        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
//        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(),chatRoom);
//        return chatRoom;
//    }
//
//    public void enterChatRoom(String roomId){
//        ChannelTopic topic = topics.get(roomId);
//        if (topic == null) {
//            topic = new ChannelTopic(roomId);
//            redisMessageListener.addMessageListener(redisSubscriber, topic);
//            topics.put(roomId, topic);
//        }
//    }

//    public ChannelTopic getTopic(String roomId) {
//        return topics.get(roomId);
//    }

}
