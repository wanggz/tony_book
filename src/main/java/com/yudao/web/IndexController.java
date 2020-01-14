package com.yudao.web;

import com.yudao.entity.Result;
import com.yudao.index.IndexReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @RequestMapping(value = "/index")
    public  String hello() {
        return "index";
    }

    private static int pageSize = 10;

    @ResponseBody
    @RequestMapping(value = "/query/{keyword}/{field}/{pageStart}/{pageNoNow}",method = RequestMethod.GET)
    public Result query(
            @PathVariable String keyword,
            @PathVariable String field,
            @PathVariable int pageStart,
            @PathVariable int pageNoNow
    ) {
        System.out.println(keyword);
        System.out.println(field);
        System.out.println(pageStart);
        System.out.println(pageNoNow);
        Result result =  IndexReader.search(pageStart,pageSize,pageNoNow,field,keyword);

        System.out.println("- - - - -");

        System.out.println(result.getNumHits());
        System.out.println(result.getPageStart());

        //ModelAndView modelAndView=new ModelAndView("/index");
        //modelAndView.clear();
        //modelAndView.addObject("rr",result);

        return result;
    }

}
