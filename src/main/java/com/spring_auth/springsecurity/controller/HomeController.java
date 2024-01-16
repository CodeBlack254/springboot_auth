package com.spring_auth.springsecurity.controller;

import com.spring_auth.springsecurity.config.Configs;
import com.spring_auth.springsecurity.config.constants.JsonResponses;
import com.spring_auth.springsecurity.dto.RegisterUserDTO;
import com.spring_auth.springsecurity.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class HomeController{
    @Autowired
    private RegisterService registerService;

    @GetMapping("/register_user")
    public ResponseEntity<?> haltOperation(){
        JsonResponses response = new JsonResponses("GET NOT ALLOWED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register_user")
    public ResponseEntity<?> registerUser(@RequestBody  RegisterUserDTO registerUserDTO){
        String expectedToken = Configs.TOKEN;

        if (!StringUtils.hasText(registerUserDTO.getToken()) || !StringUtils.hasText(registerUserDTO.getEmail())
                || !StringUtils.hasText(registerUserDTO.getName()) || !StringUtils.hasText(registerUserDTO.getPassword())) {
            JsonResponses response = new JsonResponses("Please fill all fields");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (registerUserDTO.getToken().equals(expectedToken)) {
             String name = registerUserDTO.getName();
             String email = registerUserDTO.getEmail();
             String password = registerUserDTO.getPassword();

            return registerService.registerUser(name,email,password);
        }
        else {
            JsonResponses response = new JsonResponses("Invalid Token");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}


