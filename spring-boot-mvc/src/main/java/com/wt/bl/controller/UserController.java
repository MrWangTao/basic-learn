package com.wt.bl.controller;

import com.wt.bl.dto.UserDTO;
import com.wt.bl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author WangTao
 *         Created at 18/9/3 下午1:15.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    /*@PostMapping
    public UserDTO insertEntity(@RequestBody UserDTO userDTO) {
        System.out.println(userDTO.toString());
        return userDTO;
    }*/

    @Autowired
    private UserService userService;

    @PostMapping
    public String addUser(@RequestBody @Valid UserDTO user) {
        userService.addUser(user);
        return "success";
    }

}
