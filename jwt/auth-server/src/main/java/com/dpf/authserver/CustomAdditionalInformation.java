package com.dpf.authserver;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author dpf
 * @create 2020-05-25 15:50
 * @email 446933040@qq.com
 * 在jwt中添加额外的信息
 */
@Component
public class CustomAdditionalInformation implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> information = accessToken.getAdditionalInformation();
        information.put("author","pikachues");
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(information);
        return accessToken;
    }
}
