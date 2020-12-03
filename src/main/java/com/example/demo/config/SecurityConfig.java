package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/v1/currencyUser/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/currencyUser/get/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated();

        httpSecurity.headers().frameOptions().disable();
    }

}

