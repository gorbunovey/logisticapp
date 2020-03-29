package com.gorbunovey.logisticapp.config;

import com.gorbunovey.logisticapp.security.AuthProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("com.gorbunovey.logisticapp.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // класс -> конфигурация веб-доступа

    @Autowired
    private AuthProviderImpl authProvider;
    // свой authenticationProvider для ручной аутентификации

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                        // маппим доступ:
                        // только для НЕаутентифиц. пользователей:
                        .antMatchers("/login", "/registration").anonymous()
                        // для всех
                        .antMatchers("/").permitAll()
                        // antMatchers("/admin/**").hasRole("ADMIN") - образец для роли
                        // все остальные - только для аутентифиц. пользователей
                        .anyRequest().authenticated()
                    .and()
                        // откл. защиту от атак(в этом проекте незачем + она требует logout по POST c csrf-токеном,
                        // спринговый jstl тег form уже содержит в себе csrf-токен )
                        .csrf().disable()
                        // настраиваем страницу и форму логина:
                        .formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/login/process")
                        //.defaultSuccessUrl("/homepage.html",true) // url куда редиректить при успешной аутентификации
                        .failureUrl("/login?error=true") // url куда редиректить при неудачной аутентификации
                        .usernameParameter("email")
                        .passwordParameter("password")
                    .and()
                        .exceptionHandling()
                        .accessDeniedPage("/") // url куда редиректить, когда аутентифиц. польз. идут на log|reg
                    .and()
                        .logout()
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
