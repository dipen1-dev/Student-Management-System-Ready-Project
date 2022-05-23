package com.example.registrationLogin.repository;

import com.example.registrationLogin.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findUserById(Long id);
}
