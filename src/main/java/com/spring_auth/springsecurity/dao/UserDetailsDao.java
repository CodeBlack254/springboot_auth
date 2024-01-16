package com.spring_auth.springsecurity.dao;

import com.spring_auth.springsecurity.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsDao extends JpaRepository<UserDetails, Integer> {
    //interface needs to specify the entity type (UserDetails) and the type of the primary key (Integer)
}