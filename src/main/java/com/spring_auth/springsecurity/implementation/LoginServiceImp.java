package com.spring_auth.springsecurity.implementation;
import com.spring_auth.springsecurity.config.Configs;
import com.spring_auth.springsecurity.config.constants.JsonResponses;
import com.spring_auth.springsecurity.dto.LoginUserDTO;
import com.spring_auth.springsecurity.repository.UserDetailsRepo;
import com.spring_auth.springsecurity.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoginServiceImp{
    private final UserDetailsRepo userDetailsRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public LoginServiceImp(UserDetailsRepo userDetailsRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsRepo = userDetailsRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<JsonResponses> loginUser(LoginUserDTO loginUserDTO) {
        String expectedToken = Configs.TOKEN;

        if (!StringUtils.hasText(loginUserDTO.getToken()) || !StringUtils.hasText(loginUserDTO.getEmail())
                || !StringUtils.hasText(loginUserDTO.getPassword())) {
            JsonResponses response = new JsonResponses("Please fill all fields");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (loginUserDTO.getToken().equals(expectedToken)) {
            String email = loginUserDTO.getEmail();
            String password = loginUserDTO.getPassword();

            try {
                UserDetails userDetails = userDetailsRepo.findByEmail(email);

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
            e.printStackTrace();
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

