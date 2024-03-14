package com.hwangdo.myrestfulservice.controller;

import com.hwangdo.myrestfulservice.bean.HelloWorldBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    // GET
    // URI = /hello-world
    // @RequestMapping(method=RequestMethod.GET, path="/hello-world")
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        /**
         * bean으로 전달이 되면 스프링에서는 responseBody로 변환되기 때문에 json 형태 값을 가짐
         */
        return new HelloWorldBean("Hello World!");
    }
}
