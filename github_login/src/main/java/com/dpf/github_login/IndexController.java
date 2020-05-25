package com.dpf.github_login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dpf
 * @create 2020-05-25 21:42
 * @email 446933040@qq.com
 */
@Controller
public class IndexController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/index.html")
    public String index() {
        return "index";
    }

    @GetMapping("/authorization_code")
    public String authorization_code(String code) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", "xxxx");
        map.put("client_secret", "xxxx");
        map.put("state", "pikachues");  //防止跨站脚本攻击
        map.put("code", code);
        map.put("redirect_uri", "http://localhost:8080/authorization_code");
        Map<String,String> resp = restTemplate.postForObject("https://github.com/login/oauth/access_token", map, Map.class);
        System.out.println(resp);
        HttpHeaders httpheaders = new HttpHeaders();
        httpheaders.add("Authorization", "token " + resp.get("access_token"));
        HttpEntity<?> httpEntity = new HttpEntity<>(httpheaders);
        ResponseEntity<Map> exchange = restTemplate.exchange("https://api.github.com/user", HttpMethod.GET, httpEntity, Map.class);
        //第三方用户信息，可存数据库
        System.out.println("exchange.getBody() = " + new ObjectMapper().writeValueAsString(exchange.getBody()));
        return "forward:/index.html";
    }
}
