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
    // class -> for web access configuration

    @Autowired
    private AuthProviderImpl authProvider;
    // own authenticationProvider for manual authentication

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                        // map access:
                        // for non-authenticated users only
                        .antMatchers("/login", "/registration").anonymous()
                        // for all
                        .antMatchers("/resources/**", "/**").permitAll()
                        .antMatchers("/", "/index", "/about").permitAll()
                        // by roles
                        .antMatchers("/driver/**").hasAnyRole("DRIVER", "ADMIN")
                        .antMatchers("/drivers/**", "/trucks/**","/cargo/**","/orders/**").hasAnyRole("STUFF", "ADMIN")
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        // any other - only for authenticated users
                        .anyRequest().authenticated()
                    .and()
                        // cut off csrf-attack defense(there is no need in this project + it demands logout by POST with csrf-token,
                        // PS: spring tag <form> already contains csrf-token )
                        .csrf().disable()
                        // customize the login page and form:
                        .formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/login/process")
                        .defaultSuccessUrl("/index",true) // redirect url upon successful authentication
                        .failureUrl("/login?error=true") // redirect url upon authentication failure
                        .usernameParameter("email")
                        .passwordParameter("password")
                    .and()
                        .exceptionHandling()
                        .accessDeniedPage("/") // redirect url, when authenticated users go to идут на log|reg
                    .and()
                        .logout()
                        .logoutSuccessUrl("/index")
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
