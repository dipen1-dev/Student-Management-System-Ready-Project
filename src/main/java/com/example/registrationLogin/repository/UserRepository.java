package com.example.registrationLogin.repository;

import com.example.registrationLogin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    /*It returns the type user which is find by provided email or user having this email*/

}
//Autowired is field based dependency injection we can use constructor instead of using it