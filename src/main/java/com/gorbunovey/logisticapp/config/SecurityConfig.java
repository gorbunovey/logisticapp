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
        http.authorizeRequests()
                // маппим страницы:
                // только для НЕаутентифиц. пользователей:
                .antMatchers("/login", "/registration").anonymous()
                // только для аутентифиц. пользователей
                .antMatchers("/drivers").authenticated()
                // откл. защиту от атак(временно)
                .and().csrf().disable()
                // настраиваем страницу и форму логина:
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/process")
                .failureUrl("/login?error=true") // url на кот. делать редирект при неудачной аутентификации
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/index")
                .and().logout();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
