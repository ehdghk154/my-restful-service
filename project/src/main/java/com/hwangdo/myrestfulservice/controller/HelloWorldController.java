package com.hwangdo.myrestfulservice.controller;

import com.hwangdo.myrestfulservice.bean.HelloWorldBean;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {
    private MessageSource messageSource;

    // 생성자 주입
    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // GET
    // URI = /hello-world
    // @RequestMapping(method=RequestMethod.GET, path="/hello-world")
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        // bean으로 전달이 되면 스프링에서는 responseBody로 변환되기 때문에 json 형태 값을 가짐
        return new HelloWorldBean("Hello World!");
    }

    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBeanPathVariable(@PathVariable String name) {
        // 경로 변수로 name을 데이터로 받아 로직에 사용
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("greeting.message", null, locale);
    }
}
