package com.hwangdo.myrestfulservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data // getter, setter 등 자동 생성
@AllArgsConstructor // 모든 프로퍼티들을 가진 생성자 생성
public class HelloWorldBean {
    private String message;

//    public HelloWorldBean(String message) {
//        this.message = message;
//    }
}
