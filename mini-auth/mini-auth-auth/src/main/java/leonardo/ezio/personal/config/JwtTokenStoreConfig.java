package leonardo.ezio.personal.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-26 12:08
 */
@Data
@Configuration
public class JwtTokenStoreConfig {

    private static final String SIGNING_KEY = "micro-auth";

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public TokenStore tokenStore(){
        return new RedisTokenStore(redisTemplate.getConnectionFactory());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(SIGNING_KEY);
        return jwtAccessTokenConverter;
    }
}
