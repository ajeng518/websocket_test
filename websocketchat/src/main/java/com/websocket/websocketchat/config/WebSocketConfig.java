package com.websocket.websocketchat.config;

import com.websocket.websocketchat.handler.StompHandler;
import com.websocket.websocketchat.handler.websocketChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.config.annotation.*;
@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer  {

    private final StompHandler stompHandler;
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*")
                .withSockJS();
    }

//    private final WebSocketHandler webSocketHandler;
////        @Bean
////        public WebSocketHandler handler() {
////            return new websocketChatHandler();
////        }
//
//        @Override
//        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//            registry.addHandler(webSocketHandler, "/test").setAllowedOriginPatterns("*");
//        }
}
