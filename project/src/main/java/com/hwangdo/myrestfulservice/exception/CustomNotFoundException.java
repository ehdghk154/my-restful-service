package com.hwangdo.myrestfulservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 이 Exception이 발생한 이유는 클라이언트 측에서 존재하지 않는 데이터를 요청한 잘못이기에 4xx의 상태 코드가 발생(NOT_FOUND -> 404)
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomNotFoundException extends RuntimeException {
    public CustomNotFoundException(String message) {
        // super()는 부모 클래스의 생성자를 호출할 때 사용
        // 부모 클래스의 생성자로 message 전달
        super(message);
    }
}
