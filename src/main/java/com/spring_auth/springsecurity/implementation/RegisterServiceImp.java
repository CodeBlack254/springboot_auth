package com.spring_auth.springsecurity.implementation;
import com.spring_auth.springsecurity.config.constants.JsonResponses;
import com.spring_auth.springsecurity.repository.UserDetailsRepo;
import com.spring_auth.springsecurity.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImp {
    private final UserDetailsRepo userDetailsRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RegisterServiceImp(UserDetailsRepo userDetailsRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsRepo = userDetailsRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<JsonResponses> registerUser(String name, String email, String password) {
        try{
            //register new user
            UserDetails userDetails = new UserDetails();
            userDetails.setName(name);
            userDetails.setEmail(email);
            String encodedPassword = passwordEncoder.encode(password);
            userDetails.setPassword(encodedPassword);
            userDetailsRepo.save(userDetails);

            JsonResponses response = new JsonResponses("User Created Successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch(Exception e){
//            e.printStackTrace();
            JsonResponses response = new JsonResponses("Something went wrong");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}

