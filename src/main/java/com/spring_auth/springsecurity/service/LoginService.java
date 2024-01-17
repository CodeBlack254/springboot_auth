package com.spring_auth.springsecurity.service;

import com.spring_auth.springsecurity.config.constants.JsonResponses;
import com.spring_auth.springsecurity.dao.UserDetailsDao;
import com.spring_auth.springsecurity.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    UserDetailsDao userDetailsDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public ResponseEntity<?> loginUser(String email, String password) {
        try {
            UserDetails userDetails = userDetailsDao.findByEmail(email);

            if (userDetails != null) {
                if (passwordEncoder.matches(password, userDetails.getPassword())) {
                    JsonResponses response = new JsonResponses("Login Successful");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    JsonResponses response = new JsonResponses("Invalid Password");
                    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
                }
            } else {
                JsonResponses response = new JsonResponses("User does not exist");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch(Exception e){
//            e.printStackTrace();
            JsonResponses response = new JsonResponses("Something went wrong");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }
}
