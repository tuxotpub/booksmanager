package org.tuxotpub.booksmanager.config.security;

/**
 * Created by tuxsamo.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/*import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;*/
import org.tuxotpub.booksmanager.services.security.AuthenticationUserService;

//@Configuration
//@EnableWebSecurity
//@ComponentScan(basePackageClasses = AuthenticationUserService.class)
public class WebSecurityConfig{ //extends WebSecurityConfigurerAdapter {

    /*public WebSecurityConfig(@Qualifier("authenticationUserService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()
                //.anyRequest()
                .antMatchers("/api/**").authenticated().and().httpBasic()
                //.and().formLogin().loginPage("/login").permitAll()
                .and().logout().permitAll();
    }

    @Autowired
    public void globalSecurityConfiguration(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }*/

}