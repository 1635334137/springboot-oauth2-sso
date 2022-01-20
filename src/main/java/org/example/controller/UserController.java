package org.example.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 *
 * @author Mr.Lan
 * @create: 2022-01-19 17:00
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 获取当前用户对象
     */
    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication){
        Object principal = authentication.getPrincipal();
        return principal;
    }

}
