package com.example.registrationLogin.web;

import com.example.registrationLogin.model.User;
import com.example.registrationLogin.repository.UserRepository;
import com.example.registrationLogin.services.UserService;
import com.example.registrationLogin.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    @Autowired
    private UserRepository userRepository;
    private UserRegistrationDto userRegistrationDto;
    private UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }


    @ModelAttribute("user")
    //it maps the user to userRegistrationDto object i.e indirectly stores user in the userRegistrationDto
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();// it returns the new object that stores the data of object user from index
    }

    //modelattribute name must be same as the name in th:object
    /*Another Method--------
    public String showRegistrationForm1(Model model){
        model.addAttribute("user",new UserRegistrationDto());
        return "registration";
    }
    */
    @GetMapping//this method handle http get request
    public String showRegistrationForm() {
        return "registration";
    } //this is for simply handle to show the registration form

    @PostMapping //this method handle http post request
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        Optional<User> optionalUser= Optional.ofNullable(userRepository.findByEmail(registrationDto.getEmail()));
        if(optionalUser.isPresent()){
            System.out.println("ok error found");
            return "redirect:/registration?error";
        }
        userService.save(registrationDto);
        return "redirect:/registration?success";
    }
}
