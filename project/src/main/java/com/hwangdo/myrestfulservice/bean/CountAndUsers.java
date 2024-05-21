package com.hwangdo.myrestfulservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CountAndUsers {
    private int count;
    private List<User> users;
}
