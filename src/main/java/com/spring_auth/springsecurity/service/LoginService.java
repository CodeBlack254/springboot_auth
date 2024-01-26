package com.spring_auth.springsecurity.service;

import com.spring_auth.springsecurity.constants.JsonResponses;
import com.spring_auth.springsecurity.dto.LoginUserDTO;
import com.spring_auth.springsecurity.service.imp.LoginServiceImp;
import org.springframework.http.ResponseEntity;
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
