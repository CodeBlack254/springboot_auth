package com.spring_auth.springsecurity.implementation;
import com.spring_auth.springsecurity.config.Configs;
import com.spring_auth.springsecurity.config.constants.JsonResponses;
import com.spring_auth.springsecurity.dto.RegisterUserDTO;
import com.spring_auth.springsecurity.repository.UserDetailsRepo;
import com.spring_auth.springsecurity.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RegisterServiceImp {
    private final UserDetailsRepo userDetailsRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RegisterServiceImp(UserDetailsRepo userDetailsRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsRepo = userDetailsRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<JsonResponses> registerUser(RegisterUserDTO registerUserDTO) {
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
        else {
            JsonResponses response = new JsonResponses("Invalid Token");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}

