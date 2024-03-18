package com.hwangdo.myrestfulservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class User { // Domain => 비지니스 서비스에서 사용되는 업무 영역, 규칙, 지식
    private Integer id;
    private String name;
    private Date joinDate;
}
