package com.dpf.authserver;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;

/**
 * @author dpf
 * @create 2020-05-25 23:26
 * @email 446933040@qq.com
 * 定制透明令牌jwt
 */
public class MyJwt extends JwtAccessTokenConverter {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        LinkedHashMap<String, Object> addtionalInfomation = new LinkedHashMap<>();
        LinkedHashMap<String, Object> info = new LinkedHashMap<>();
        info.put("author","pikachues");
        info.put("email","pikachues@qq.com");
        info.put("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        addtionalInfomation.put("info",info);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(addtionalInfomation);
        return super.enhance(accessToken, authentication);
    }
}
