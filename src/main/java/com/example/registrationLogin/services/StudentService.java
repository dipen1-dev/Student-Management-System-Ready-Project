package com.example.registrationLogin.services;


import com.example.registrationLogin.exceptions.ResourceNotFoundException;
import com.example.registrationLogin.model.Student;
import com.example.registrationLogin.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> listAllStudent() {
        return studentRepository.findAll();
    }

    public Student save(Student std) {
        return studentRepository.save(std);
    }

    public Student get(long rollNo) {
        return studentRepository.findById(rollNo).orElseThrow(() -> new ResourceNotFoundException("Student", "rollNo", rollNo));
    }
    public Student get(String email){
        return studentRepository.findByLoggedInEmail(email);
    }

    public Student update(Student std, long rollNo) {
        Student student = studentRepository.findById(rollNo).orElseThrow(() -> new ResourceNotFoundException("Student", "rollNo", rollNo));
        student.setRollNo(std.getRollNo());
        student.setEntranceRollNo(std.getEntranceRollNo());
        student.setFirstName(std.getFirstName());
        student.setLastName(std.getLastName());
        student.setAddress(std.getAddress());
        student.setDOB(std.getDOB());
        student.setEntranceScore(std.getEntranceScore());
        student.setIdentityNo(std.getIdentityNo());
        return studentRepository.save(student);
    }

    public void delete(long rollNo) {
        studentRepository.deleteById(rollNo);
    }
}

