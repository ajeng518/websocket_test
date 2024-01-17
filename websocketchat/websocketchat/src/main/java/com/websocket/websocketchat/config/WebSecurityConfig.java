package com.websocket.websocketchat.config;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Web Security 설정
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig  {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.csrf(csrf->csrf.);
        http
//                .headers(header -> header
//                        .frameOptions().sameOrigin())
                //.csrf().disable().cors().disable() 더이상 지원안한다고 쓰지말래.. 왜?
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/chat/**").hasRole("USER") // 해당권한을 가진 사람만 인증가능
                        .anyRequest().permitAll() // 나머지는 인증없이 접근 가능
                )
                .formLogin(withDefaults())
                .logout(withDefaults());

        //request -> request
//                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//                        .anyRequest().authenticated()   // 어떠한 요청이라도 인증필요
        //    )


        return http.build();
    }
    @Bean
    public InMemoryUserDetailsManager userDetailService() {
        UserDetails user1 = //핵심 정보로 사용자의 인증 정보를 생성
                User.builder()   // 패스워드를 암호화
                        .username("sung") // usrname(id) 설정
                        .password(passwordEncoder().encode("1111"))           // password 설정
                        .roles("USER")             // 역할을 지정
                        .build();
        UserDetails user2 =
                User.builder()     // 패스워드를 암호화
                        .username("yeo") // usrname(id) 설정
                        .password(passwordEncoder().encode("1111"))                // password 설정
                        .roles("USER")               // 역할을 지정
                        .build();
        UserDetails user3 =
                User.builder()      // 패스워드를 암호화
                        .username("kim") // usrname(id) 설정
                        .password(passwordEncoder().encode("1111"))                // password 설정
                        .roles("GUEST")               // 역할을 지정
                        .build();

        return new InMemoryUserDetailsManager(user1, user2, user3); //DI하여 빈 등록
    }
}