package com.boltech.service_insurance.service;

import com.boltech.service_insurance.constant.ErrorCode;
import com.boltech.service_insurance.constant.UserConstants;
import com.boltech.service_insurance.dto.SignUpUserRequest;
import com.boltech.service_insurance.exception.ApplicationException;
import com.boltech.service_insurance.model.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserManagementService {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void signUp(@Valid SignUpUserRequest signUpUserRequest) {

       if (userService.findByEmail(signUpUserRequest.getEmail()).isPresent()) {
           throw new ApplicationException(ErrorCode.USER_ALREADY_EXISTS, "User already exist with email: " + signUpUserRequest.getEmail());
       }

       User user = new User();
       user.setEmail(signUpUserRequest.getEmail());
       user.setFirstName(signUpUserRequest.getFirstName());
       user.setLastName(signUpUserRequest.getLastName());
       user.setPassword(passwordEncoder.encode(signUpUserRequest.getPassword()));
       user.setStatus(UserConstants.Status.ACTIVE);
       user.setRoles(List.of(UserConstants.Role.USER));
       userService.save(user);
    }
}
