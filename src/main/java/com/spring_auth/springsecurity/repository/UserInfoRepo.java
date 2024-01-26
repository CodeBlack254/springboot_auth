package com.spring_auth.springsecurity.repository;

import com.spring_auth.springsecurity.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//interface needs to specify the entity type (UserDetails) and the type of the primary key (Integer)
@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Integer> {
//    @Query(value = "SELECT * FROM user_details ud WHERE ud.email=:email", nativeQuery = true)
    UserInfo findByEmail(String email);
}