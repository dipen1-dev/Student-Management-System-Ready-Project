package com.example.registrationLogin.services;

import com.example.registrationLogin.model.Role;
import com.example.registrationLogin.model.User;
import com.example.registrationLogin.repository.RoleRepository;
import com.example.registrationLogin.repository.UserRepository;
import com.example.registrationLogin.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    /*@Autowired UserRepository userRepository;
    this is the field base injection here we inject the UserRepository in the Service
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    //lets now inject using constructor based injector


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // this helps to inject the object of userRepository in the UserServiceImpl
    @Override// it implements the method of interface
    public User save(UserRegistrationDto registrationDto) {

        User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(),
                registrationDto.getEmail(), passwordEncoder.encode(registrationDto.getPassword()),Arrays.asList(new Role("ROLE_ADMIN")));
        //here object of role is created, it called the constructor of Role
        /*Here passwordEncoder.encode() which is the object of BcryptPasswordEncoder encode the password which is stored in database*/
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        /*this is a method which maps the roles to the authorities*
        Here this collection is extended to the grantedAuthority
        Here collection collects the roles which is of tye Role(Object)
         */
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        /*We just converted roles into stream on top stream we map a role we convert role into simplegrantedAsuthority
         * object in this class we pass the role name finally we collected stream into the list and finally this list
         * is returned to the UserDetails*/
    }

}
