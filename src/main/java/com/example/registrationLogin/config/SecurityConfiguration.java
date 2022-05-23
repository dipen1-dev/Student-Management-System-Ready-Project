package com.example.registrationLogin.config;

import com.example.registrationLogin.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
//it enables web security support
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    /* provides convenient base class for websecurityconfiguration the implementation allows customization
    by overriding method
     */

    // @Autowired
    /*Autowired is field base injection which inject object of one class to another class*/
    private UserService userService;

    public SecurityConfiguration(@Lazy UserService userService) {//lazy to break cycle
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder
        /*Implementation of PasswordEncoder that uses the BCrypt strong hashing function.*/ passwordEncoder() {
        return new BCryptPasswordEncoder();
//        this returns the encoded password
    }

    @Override
    /*overrinding the configure method of websecurityconfigure*/
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(
//                Allows restricting access based upon the HttpServletRequest using RequestMatcher implementations (i.e. via URL patterns)v
                        "/registration/**").permitAll()
                .antMatchers("/api/v1/students/{id}/**").permitAll()
                .antMatchers("/api/v1/students/**").permitAll()
                .antMatchers("/js/**",
                        "/css/**",
                        "/img/**").permitAll()
                .antMatchers("/users").hasAnyAuthority("ROLE_ADMIN")
//                Specify that URLs are allowed by anyone.
                .anyRequest().authenticated()
//               anyRequest map any request,Specify that URLs are allowed by any authenticated user.
                .and()
                .formLogin()
//              to support form based authentication.
                .loginPage("/login").defaultSuccessUrl("/welcome")
                /*Specifies the URL to send users to if login is required*/
                .permitAll()
                /* permiting all the authenticated user from login page */
                .and()
                .logout()
                .invalidateHttpSession(true)
                /*Performs a logout by modifying the SecurityContextHolder.Will also invalidate the HttpSession
                if isInvalidateHttpSession() is tru
                 */
                .clearAuthentication(true)
                /*Specifies if SecurityContextLogoutHandler should clear the Authentication at the time of logout
                 */
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                /*The RequestMatcher that triggers log out to occur,Creates a matcher with the specific pattern
                 which will match all HTTP methods in a case sensitive manner.
                 */
                .logoutSuccessUrl("/login?logout")
                /*The URL to redirect to after logout has occurred.if logout success it redirects to the login with success
                 message of logout
                 */
                .permitAll();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        /*DaoAuthenticationProvider, An AuthenticationProvider implementation that retrieves user details
        from a UserDetailsService.
         */
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        /*this is dependency injection*/
        auth.setPasswordEncoder(passwordEncoder());
        /*Sets the PasswordEncoder instance to be used to encode and validate passwords*/
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
        /*
Add authentication based upon the custom AuthenticationProvider that is passed in. Since the AuthenticationProvider implementation is unknown,
 all customizations must be done externally and the AuthenticationManagerBuilder is returned immediately.*/
        /*AuthenticationManagerBuilder, SecurityBuilder used to create an AuthenticationManager. Allows for easily building in memory authentication, LDAP authentication,
        JDBC based authentication, adding UserDetailsService, and adding AuthenticationProvider's.
         */
    }
}