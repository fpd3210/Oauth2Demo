package com.dpf.client1;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author dpf
 * @create 2020-05-25 20:44
 * @email 446933040@qq.com
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName()+ Arrays.toString(authentication.getAuthorities().toArray());
    }
}
