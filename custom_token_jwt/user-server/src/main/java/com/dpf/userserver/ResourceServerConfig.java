package com.dpf.userserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author dpf
 * @create 2020-05-22 22:13
 * @email 446933040@qq.com
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    TokenStore tokenStore;


    /**
     * 配置tokenStore之后，资源服务器不再需要配置远程校验地址。
     * 这里会自动调用 JwtAccessTokenConverter 将 jwt 解析出来，
     * jwt 里边就包含了用户的基本信息，所以就不用远程校验 access_token 了。
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("res1").tokenStore(tokenStore);
    }

    /**
     * 配置资源拦截规则
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("admin")
                .anyRequest().authenticated();
    }

}
