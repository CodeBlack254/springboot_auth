package com.spring_auth.springsecurity.service;

import com.spring_auth.springsecurity.config.constants.JsonResponses;
import com.spring_auth.springsecurity.implementation.LoginServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final LoginServiceImp loginServiceImp;

    public LoginService(LoginServiceImp loginServiceImp) {
        this.loginServiceImp = loginServiceImp;
    }

    public ResponseEntity<JsonResponses> loginUser(String email, String password) {
        return loginServiceImp.loginUser(email, password);
    }

}
