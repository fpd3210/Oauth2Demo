package com.dpf.authserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author dpf
 * @create 2020-05-22 21:25
 * @email 446933040@qq.com
 *
 * 配置授权服务器：
 * 授权服务器要做两方面的检验，一方面是校验客户端，另一方面则是校验用户
 */
@Configuration
@EnableAuthorizationServer  //开启授权服务器的自动化配置
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter  {

    @Autowired
    TokenStore tokenStore;

    @Autowired
    ClientDetailsService clientDetailsService;

    /**
     * 对授权服务器进一步配置
     * 主要用来配置 Token 的一些基本信息，例如 Token 是否支持刷新、Token 的存储位置、Token 的有效期以及刷新 Token 的有效期等等
     * @return
     */
    @Bean
    AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);
        services.setSupportRefreshToken(true);
        services.setTokenStore(tokenStore);
        services.setAccessTokenValiditySeconds(60*60*2);
        services.setRefreshTokenValiditySeconds(60*60*24*3);
        return services;
    }

    /**
     * (校验用户)用来配置令牌端点的完全约束，也就是这个端点谁能访问，谁不能访问
     * checkTokenAccess 是指一个 Token 校验的端点，这里设置可以直接访问
     * （在后面，当资源服务器收到 Token 之后，需要去校验 Token 的合法性，就会访问这个端点）。
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll")
                .allowFormAuthenticationForClients();
    }

    /**
     * ClientDetailsServiceConfigurer用来配置客户端详细信息
     * 这里配置客户端校验(客户端信息可以存在数据库中)
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("pikachues")
                .secret(new BCryptPasswordEncoder().encode("123"))
                .resourceIds("res1")  // 客户端id
                .authorizedGrantTypes("client_credentials","refresh_token")  //授权类型，四种类型
                .scopes("all")  // 授权范围
                .redirectUris("http://localhost:8083/index.html");  //用户登录成功/失败后调转的地址
    }

    /**
     * AuthorizationServerEndpointsConfigurer 这里用来配置令牌的访问端点和令牌服务。
     * authorizationCodeServices 用来配置授权码的存储
     * tokenServices 用来配置令牌的存储 即 access_token 的存储位置
     * 授权码是用来获取令牌的，使用一次就失效，令牌则是用来获取资源的，
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authorizationCodeServices(authorizationCodeServices())
                .tokenServices(tokenServices());
    }

    @Bean
    AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }
}
