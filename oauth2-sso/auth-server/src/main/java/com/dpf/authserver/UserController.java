package com.dpf.authserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author dpf
 * @create 2020-05-25 20:33
 * @email 446933040@qq.com
 */
@RestController
public class UserController {

    @GetMapping("/user")
    public Principal getCurrentUser(Principal principal){
        return principal;
    }
}
