package com.yudao.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping(value = "/hello")
    public  String hello() {
        return "hello";
    }

}
