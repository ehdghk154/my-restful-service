package com.hwangdo.myrestfulservice.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    // fetch
    // -FetchType.EAGER => 즉시 로딩 -> user가 로딩될 때 post도 같이 로딩됨(실무에서 가급적 사용 하지 않음)
    // -FetchType.LAZY => 지연 로딩 -> user가 로딩될 때 바로 post가 로딩되지 않고, 실제로 post를 사용할 때 로딩됨.
    // Post : User = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
}
