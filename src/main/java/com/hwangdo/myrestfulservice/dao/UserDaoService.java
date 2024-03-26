package com.hwangdo.myrestfulservice.dao;

import com.hwangdo.myrestfulservice.bean.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

// @Service를 사용할 수 있지만 DAO와 Service 기능을 혼합한 클래스이기 때문에
// 일반적인 Component로 등록
@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new User(1, "Dante", new Date(), "test1", "900101-1111111"));
        users.add(new User(2, "Sendi", new Date(), "test2", "950111-2222222"));
        users.add(new User(3, "Volta", new Date(), "test3", "930121-1234567"));
    }

    public User save(User user) {
        if(user.getId() == null) {
            user.setId(++usersCount);
        }

        if(user.getJoinDate() == null) {
            user.setJoinDate(new Date());
        }

        users.add(user);

        return user;
    }

    public List<User> findAll() {
        return users;
    }

    public User findOne(int id) {
        for(User user : users) {
            if(user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();

        while(iterator.hasNext()) {
            User user = iterator.next();

            if(user.getId() == id) {
                iterator.remove();
                return user;
            }
        }

        return null;
    }
}
