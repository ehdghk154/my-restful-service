package com.hwangdo.myrestfulservice.repository;

import com.hwangdo.myrestfulservice.bean.Post;
import com.hwangdo.myrestfulservice.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
