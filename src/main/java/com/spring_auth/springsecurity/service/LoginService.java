package com.spring_auth.springsecurity.service;

import com.spring_auth.springsecurity.config.constants.JsonResponses;
import com.spring_auth.springsecurity.dto.LoginUserDTO;
import com.spring_auth.springsecurity.implementation.LoginServiceImp;
import com.spring_auth.springsecurity.model.UserDetails;
import com.spring_auth.springsecurity.repository.UserDetailsRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final LoginServiceImp loginServiceImp;

    public LoginService(LoginServiceImp loginServiceImp) {
        this.loginServiceImp = loginServiceImp;
    }

    public ResponseEntity<JsonResponses> loginUser(LoginUserDTO loginUserDTO) {
        return loginServiceImp.loginUser(loginUserDTO);
    }

}
