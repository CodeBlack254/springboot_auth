package com.spring_auth.springsecurity.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Subjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subjectId;

    private String subjectName;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userInfo;

    // constructors, getters, setters
}
