package com.wt.bl.controller;

import com.wt.bl.entity.User;
import com.wt.bl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author WangTao
 *         Created at 18/9/3 下午1:15.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /*@PostMapping
    public UserDTO insertEntity(@RequestBody UserDTO userDTO) {
        System.out.println(userDTO.toString());
        return userDTO;
    }*/

    @Autowired
    private UserService userService;

    @PostMapping
    public String addUser(@RequestBody @Valid User user) {
        userService.addUser(user);
        return "success";
    }

    @GetMapping("/selectOne")
    public User findUserByUsernae(String username) {
        return userService.findByUsername(username);
    }

}
