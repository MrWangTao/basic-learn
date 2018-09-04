package com.wt.bl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author WangTao
 *         Created at 18/9/3 上午10:11.
 */
@Controller
@RequestMapping("/index")
public class IndexController {
    
    /**
     * @description 进入index页
     *
     * @param
     * @author wangtao
     * @return 
     * @date 18/9/3
     * @time 上午10:12
     **/
    @GetMapping
    public String index() {
        return "index";
    }

}
