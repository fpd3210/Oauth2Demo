package com.dpf.clientapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author dpf
 * @create 2020-05-22 23:04
 * @email 446933040@qq.com
 */
@Controller
public class HelloController {
    @Autowired
    TokenTask tokenTask;

    @GetMapping("/index.html")
    public String hello(String code, Model model) {
        model.addAttribute("msg", tokenTask.getData(code));
        return "index";
    }
}
