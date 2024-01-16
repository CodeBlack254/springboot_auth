
package com.spring_auth.springsecurity.service;
import com.spring_auth.springsecurity.config.constants.JsonResponses;
import com.spring_auth.springsecurity.dao.UserDetailsDao;
import com.spring_auth.springsecurity.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    UserDetailsDao userDetailsDao;
    public ResponseEntity<String> registerUser(String name, String email, String password) {
        //register new user
        UserDetails userDetails = new UserDetails();
        userDetails.setName(name);
        userDetails.setEmail(email);
        userDetails.setPassword(password);
        userDetailsDao.save(userDetails);

//        JsonResponses response = new JsonResponses("User Created Successfully");
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
        return new ResponseEntity<>("User Created Successfully", HttpStatus.CREATED);
    }
}
