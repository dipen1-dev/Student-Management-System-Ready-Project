package com.example.registrationLogin.web;

import com.example.registrationLogin.model.Student;
import com.example.registrationLogin.repository.StudentRepository;
import com.example.registrationLogin.services.StudentService;
import com.example.registrationLogin.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    HttpSession session;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/users")
    public String adminControl(Model model) {
        List<Student> allList = studentService.listAllStudent();
        model.addAttribute("liststudent", allList);
        return "users";
    }
    @GetMapping("/accept/{rollNo}/{loggedInEmail}")
    public String accept(@PathVariable("rollNo") Long rollNo, @PathVariable("loggedInEmail") String loggedInEmail){
        session.setAttribute("message","Accept");
        Student student= studentService.get(loggedInEmail);
        student.setAssignedMessage("Accepted");
        studentRepository.save(student);
        return "redirect:/users?success";
    }
    @RequestMapping("/delete/{rollNo}/{loggedInEmail}")
    public String delete(@PathVariable("rollNo") Long rollNo, @PathVariable("loggedInEmail") String loggedInEmail) {
//        studentService.delete(rollNo);
       Student student= studentService.get(loggedInEmail);
        System.out.println(student);
       student.setAssignedMessage("Rejected");
       studentRepository.save(student);
//       session.setAttribute("meessageStudent",student);
        session.setAttribute("message","Delete");
        return "redirect:/users?success";
    }
//    @GetMapping("/apply")
//    public User apply(@ModelAttribute UserRegistrationDto userRegistrationDto){
//        model.addAttribute("student",userRegistrationDto);
//        return userService.save(userRegistrationDto);
//    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        session.setAttribute("username", getLoggedinUserName());
        Student student=new Student();
        Optional<Student> optionalStudent= Optional.ofNullable(studentService.get(((String) session.getAttribute("username"))));
        if(optionalStudent.isPresent())
        {
            student=studentService.get((String) session.getAttribute("username"));
            if(student.getAssignedMessage()==null){
                student.setAssignedMessage(" ");
            }
            //studentService.save(student);
            model.addAttribute("student", student);
            return "welcome";
        }

        return "redirect:/login?failed";
    }

    private String getLoggedinUserName() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }

        return principal.toString();
    }
}
