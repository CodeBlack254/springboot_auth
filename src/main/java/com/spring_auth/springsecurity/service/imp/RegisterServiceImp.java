package com.spring_auth.springsecurity.service.imp;
import com.spring_auth.springsecurity.config.Configs;
import com.spring_auth.springsecurity.constants.JsonResponses;
import com.spring_auth.springsecurity.dto.RegisterUserDTO;
import com.spring_auth.springsecurity.repository.UserInfoRepo;
import com.spring_auth.springsecurity.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RegisterServiceImp {
    private final UserInfoRepo userInfoRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RegisterServiceImp(UserInfoRepo userInfoRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userInfoRepo = userInfoRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<JsonResponses> registerUser(RegisterUserDTO registerUserDTO) {
        String expectedToken = Configs.TOKEN;

        if (!StringUtils.hasText(registerUserDTO.getToken()) || !StringUtils.hasText(registerUserDTO.getEmail())
                || !StringUtils.hasText(registerUserDTO.getName()) || !StringUtils.hasText(registerUserDTO.getPassword())
                || !StringUtils.hasText(registerUserDTO.getRoles())) {
            JsonResponses response = new JsonResponses("Please fill all fields");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (registerUserDTO.getToken().equals(expectedToken)) {
            String name = registerUserDTO.getName();
            String email = registerUserDTO.getEmail();
            String password = registerUserDTO.getPassword();
            String roles = registerUserDTO.getRoles();

            try{
                //register new user
                UserInfo userInfo = new UserInfo();
                userInfo.setName(name);
                userInfo.setEmail(email);
                userInfo.setRoles(roles);
                String encodedPassword = passwordEncoder.encode(password);
                userInfo.setPassword(encodedPassword);
                userInfoRepo.save(userInfo);

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

