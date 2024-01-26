package com.spring_auth.springsecurity.service.imp.jwtImp;

import com.spring_auth.springsecurity.model.UserInfo;
import com.spring_auth.springsecurity.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    public UserInfoRepo userInfoRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = Optional.ofNullable(userInfoRepo.findByEmail(email));
        return userInfo.map(UserInfoImp::new)
                .orElseThrow(() -> new UsernameNotFoundException(email + " not found "));
    }
}
