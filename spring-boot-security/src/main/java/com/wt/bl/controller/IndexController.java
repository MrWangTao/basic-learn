package com.wt.bl.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangTao
 *         Created at 18/10/8 下午4:52.
 */
@RestController
public class IndexController {

    @PostMapping("/login")
    public String login() {
        return "OK";
    }

}
