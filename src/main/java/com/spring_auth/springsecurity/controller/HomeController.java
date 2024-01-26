package com.spring_auth.springsecurity.controller;

import com.spring_auth.springsecurity.constants.JsonResponses;
import com.spring_auth.springsecurity.dto.LoginUserDTO;
import com.spring_auth.springsecurity.dto.RegisterUserDTO;
import com.spring_auth.springsecurity.config.JwtUtil;
import com.spring_auth.springsecurity.service.LoginService;
import com.spring_auth.springsecurity.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from this origin
public class HomeController{
    private final RegisterService registerService;
    private final LoginService loginService;
    private final JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public HomeController(JwtUtil jwtUtil, RegisterService registerService, LoginService loginService) {
        this.registerService = registerService;
        this.loginService = loginService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/register_user")
    public ResponseEntity<?> stopRegistration(){
        JsonResponses response = new JsonResponses("GET NOT ALLOWED");
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PostMapping("/register_user")
    public ResponseEntity<?> registerUser(@RequestBody  RegisterUserDTO registerUserDTO){
        return registerService.registerUser(registerUserDTO);
    }

    @GetMapping("/login")
    public ResponseEntity<?> stopLogin(){
        JsonResponses response = new JsonResponses("GET NOT ALLOWED");
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserDTO loginUserDTO){
        return loginService.loginUser(loginUserDTO);
    }

    @PostMapping("/student_dashboard")
    @PreAuthorize("hasAuthority('student')")
    public ResponseEntity<?> student_dashboard(){
        JsonResponses response = new JsonResponses("You are now viewing the student dashboard");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/teacher_dashboard")
    @PreAuthorize("hasAuthority('teacher')")
    public ResponseEntity<?> teacher_dashboard(){
        JsonResponses response = new JsonResponses("You are now viewing the teacher dashboard");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody LoginUserDTO loginUserDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.getEmail(),loginUserDTO.getPassword()));
        if (authentication.isAuthenticated()){
            return jwtUtil.generateToken(loginUserDTO.getEmail());
        }else{
            throw new UsernameNotFoundException("Invalid user request");
        }

    }

}



