package com.yudao.web;

import com.yudao.index.IndexReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(value = "/index")
    public  String hello() {
        return "index";
    }

    private static int pageSize = 10;

    @RequestMapping(value = "/query/{keyword}/{field}/{pageStart}",method = RequestMethod.GET)
    public String query(
            @PathVariable String keyword,
            @PathVariable String field,
            @PathVariable int pageStart
    ) {
        System.out.println(keyword);
        System.out.println(field);
        System.out.println(pageStart);
        IndexReader.search(pageStart,pageSize,field,keyword);
        return "";
    }



}
