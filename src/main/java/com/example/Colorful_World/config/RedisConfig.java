package com.example.Colorful_World.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    private final String host;

    private final int port;

    private final String password;

    public RedisConfig(@Value("${spring.data.redis.host}") final String host,
                       @Value("${spring.data.redis.port}") final int port,
                       @Value("${spring.data.redis.password}") final String password){
        this.host = host;
        this.port = port;
        this.password = password;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        //single node에 redis를 연결하기 위한 설정 정보 저장
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        //Spring - Redis 간 데이터 직렬화, 역직렬화 시 사용하는 방식이 Jdk 직렬화 방식
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}
