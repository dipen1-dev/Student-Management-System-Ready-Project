package com.example.registrationLogin.web;

import com.example.registrationLogin.model.Student;
import com.example.registrationLogin.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students/")
//        http://localhost:7082/api/v1/students

public class StudentController {
    @Autowired
    private StudentService studentService;

    //    build create student REST API
    @PostMapping()
    public Student createStudent(@RequestBody Student student) {
        System.out.println(student);
        return studentService.save(student);
    }

    //    build get Student by rollNo REST API
    @GetMapping("{rollNo}")
    public ResponseEntity<Student> getStudentByrollNo(@PathVariable long rollNo) {
//        responseentity is to provide  complete response to this api
//        pathvariable extracts rollNo from URL
        return new ResponseEntity<Student>(studentService.get(rollNo), HttpStatus.OK);
    }

    //    build edit Student by rollNo REST API
    @PutMapping("{rollNo}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable long rollNo) {
        return new ResponseEntity<Student>(studentService.update(student, rollNo), HttpStatus.OK);
    }

    //        build delete student by rollNo REST API
    @DeleteMapping("{rollNo}")
    public ResponseEntity<String> deleteStudent(@PathVariable long rollNo) {
        studentService.delete(rollNo);
        return new ResponseEntity<String>("Student deleted Successfully", HttpStatus.OK);

    }
}
