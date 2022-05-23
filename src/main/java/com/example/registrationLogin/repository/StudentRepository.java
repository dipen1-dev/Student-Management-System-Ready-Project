package com.example.registrationLogin.repository;

import com.example.registrationLogin.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByLoggedInEmail(String email);
}
