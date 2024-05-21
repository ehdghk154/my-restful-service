package com.hwangdo.myrestfulservice.repository;

import com.hwangdo.myrestfulservice.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
