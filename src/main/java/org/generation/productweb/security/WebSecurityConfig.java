package org.generation.productweb.security;

import org.generation.productweb.component.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.web.authentication.*;
import org.springframework.web.bind.annotation.*;

import javax.sql.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    //AuthenticationManagerBuilder :  Allows for easily building in JDBC based authentication
    //.usersByUsernameQuery:  Sets the query to be used for finding a user by their username
    //.authoritiesByUsernameQuery: Sets the query to be used for finding a user's authorities by their username.
    // username=? : represents a parameter to be supplied by client

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, role from users where username=?")
        ;
    }

    /*public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
       return new SuccessHandler();
    }*/

    //to configure form-based authentication, we override the configure(HttpSecurity) method
    //.antMatchers: http.antMatcher() tells Spring to only configure HttpSecurity if the path matches this pattern.
    //CSRF stands for Cross-Site Request Forgery. It is an attack that forces an end user to execute unwanted actions
    // on a web application in which they are currently authenticated.

    @CrossOrigin
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.formLogin().loginPage("/login");

        http.formLogin()
                .defaultSuccessUrl("/productform");

        http.logout()
                .logoutSuccessUrl("/index");

        http.authorizeRequests()
                .antMatchers("/", "/products", "/aboutus").permitAll()
                .antMatchers("/productform/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().permitAll();
    }

    //logout method to configure log-off functionality
    //This will provide the default configuration for logging out.
    // For instance, the URL will be /logout and sessions will be invalidated.

}
