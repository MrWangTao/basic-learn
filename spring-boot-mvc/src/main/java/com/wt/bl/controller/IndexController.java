package com.wt.bl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wt.bl.entity.User;
import com.wt.bl.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author WangTao
 *         Created at 18/9/3 上午10:11.
 */
@Controller
@RequestMapping
public class IndexController {

    @Autowired
    UserService userService;
    
    /**
     * @description 进入index页
     *
     * @param
     * @author wangtao
     * @return 
     * @date 18/9/3
     * @time 上午10:12
     **/
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login_page")
    public String loginPage() {
        return "login_page";
    }

    @GetMapping("/login")
    public String index(String username, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            uae.printStackTrace();
        }
        if (subject.isAuthenticated()) {
            System.out.println("fail");
        }
        return "index";
    }

    /**
     * 注册用户
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/register")
    public String register(@RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return "数据绑定失败";
        }
        userService.addUser(user);
        return "success";
    }

}
