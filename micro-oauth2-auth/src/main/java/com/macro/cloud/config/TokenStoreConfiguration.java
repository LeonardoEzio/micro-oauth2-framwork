package com.macro.cloud.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-26 12:08
 */
@Data
@Configuration
public class TokenStoreConfiguration {

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public RedisTokenStore redisTokenStore(){
        return new RedisTokenStore(redisTemplate.getConnectionFactory());
    }
}
