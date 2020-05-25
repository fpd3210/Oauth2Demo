package com.dpf.authserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author dpf
 * @create 2020-05-22 21:24
 * @email 446933040@qq.com
 */
@Configuration
public class AccessTokenConfig {

    //jwt生成字符串的签名
    private String SIGNING_KEY = "pikachues";

    /**
     * 你生成的token要往哪里存，可以存放在redis中，也可以存放在内存中
     * 这里存放于内存中
     * @return
     */
    @Bean
    TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }
}
