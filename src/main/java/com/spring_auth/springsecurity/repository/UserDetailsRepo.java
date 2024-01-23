package com.spring_auth.springsecurity.repository;

import com.spring_auth.springsecurity.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//interface needs to specify the entity type (UserDetails) and the type of the primary key (Integer)
@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetails, Integer> {
//    @Query(value = "SELECT * FROM user_details ud WHERE ud.email=:email", nativeQuery = true)
    UserDetails findByEmail(String email);
}
