package com.hwangdo.myrestfulservice.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.hwangdo.myrestfulservice.bean.AdminUser;
import com.hwangdo.myrestfulservice.bean.AdminUserV2;
import com.hwangdo.myrestfulservice.bean.User;
import com.hwangdo.myrestfulservice.dao.UserDaoService;
import com.hwangdo.myrestfulservice.exception.CustomNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private final UserDaoService service;

    // 의존성 주입 (생성자 주입)
    public AdminUserController(UserDaoService service) {
        this.service = service;
    }
    // URI와 Parameter 방식은 일반 브라우저에서 실행 가능 (http://~.~.~/v1/~ , http://~.~.~/1?version=1)
//    @GetMapping("/v1/users/{id}") // URI로 버전 관리
//    @GetMapping(value = "/users/{id}", params = "version=1") // Parameter로 버전 관리
    // headers와 mime type 방식은 일반 브라우저에서 실행 불가
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1") // header 값으로 버전 관리
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json") // MIME-Type으로 버전 관리
    public MappingJacksonValue retrieveUser4Admin(@PathVariable int id) {
        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();

        if(user == null) {
            throw new CustomNotFoundException(String.format("ID[%s] not found", id));
        }else {
            BeanUtils.copyProperties(user, adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

//    @GetMapping("/v2/users/{id}")
//    @GetMapping(value = "/users/{id}", params = "version=2")
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUser4AdminV2(@PathVariable int id) {
        User user = service.findOne(id);

        AdminUserV2 adminUser = new AdminUserV2();

        if(user == null) {
            throw new CustomNotFoundException(String.format("ID[%s] not found", id));
        }else {
            BeanUtils.copyProperties(user, adminUser);
            adminUser.setGrade("VIP");
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","grade");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers4Admin() {
        List<User> users = service.findAll();

        List<AdminUser> adminUsers = new ArrayList<>();
        AdminUser adminUser = null;

        for(User user : users) {
            adminUser = new AdminUser();
            BeanUtils.copyProperties(user, adminUser);
            adminUsers.add(adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
        mapping.setFilters(filters);

        return mapping;
    }
}
