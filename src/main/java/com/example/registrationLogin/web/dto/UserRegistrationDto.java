package com.example.registrationLogin.web.dto;

public class UserRegistrationDto {
    /*dto is datatransferobject  this is a design pattern to use dto object to transfer between server and
    client and vice versa instead of passing instead of passing single information it is  used to pass bulck
            infromation between client and server*/


    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
