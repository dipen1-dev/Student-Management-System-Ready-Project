package com.example.registrationLogin.services;

import com.example.registrationLogin.model.User;
import com.example.registrationLogin.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    /*Here extending UserService to UserDetailsService provides the user details to the DaoAuthentication
      provider*/

    User save(UserRegistrationDto registrationDto); //interface ma no implementation
}
