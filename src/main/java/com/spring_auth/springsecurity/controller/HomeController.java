package com.spring_auth.springsecurity.controller;

import com.spring_auth.springsecurity.config.Configs;
import com.spring_auth.springsecurity.config.constants.JsonResponses;
import com.spring_auth.springsecurity.dto.LoginUserDTO;
import com.spring_auth.springsecurity.dto.RegisterUserDTO;
import com.spring_auth.springsecurity.service.LoginService;
import com.spring_auth.springsecurity.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from this origin
public class HomeController{
    private final RegisterService registerService;
    private final LoginService loginService;
    @Autowired
    public HomeController(RegisterService registerService, LoginService loginService) {
        this.registerService = registerService;
        this.loginService = loginService;
    }


    @GetMapping("/register_user")
    public ResponseEntity<?> stopRegistration(){
        JsonResponses response = new JsonResponses("GET NOT ALLOWED");
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
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

    @GetMapping("/login")
    public ResponseEntity<?> stopLogin(){
        JsonResponses response = new JsonResponses("GET NOT ALLOWED");
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserDTO loginUserDTO){
        String expectedToken = Configs.TOKEN;

        if (!StringUtils.hasText(loginUserDTO.getToken()) || !StringUtils.hasText(loginUserDTO.getEmail())
                || !StringUtils.hasText(loginUserDTO.getPassword())) {
            JsonResponses response = new JsonResponses("Please fill all fields");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (loginUserDTO.getToken().equals(expectedToken)) {
            String email = loginUserDTO.getEmail();
            String password = loginUserDTO.getPassword();

            return loginService.loginUser(email,password);
        }
        else {
            JsonResponses response = new JsonResponses("Invalid Token");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}



