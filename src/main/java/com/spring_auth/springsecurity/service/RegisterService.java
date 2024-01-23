
package com.spring_auth.springsecurity.service;
import com.spring_auth.springsecurity.config.constants.JsonResponses;
import com.spring_auth.springsecurity.implementation.RegisterServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final RegisterServiceImp registerServiceImp;

    public RegisterService(RegisterServiceImp registerServiceImp) {
        this.registerServiceImp = registerServiceImp;
    }

    public ResponseEntity<JsonResponses> registerUser(String name, String email, String password) {
        return registerServiceImp.registerUser(name, email, password);
    }

}
