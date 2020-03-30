package com.gorbunovey.logisticapp.security;

import com.gorbunovey.logisticapp.dto.UserDTO;
import com.gorbunovey.logisticapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthProviderImpl implements AuthenticationProvider {
    // класс -> свой провайдер для аутентификации, можно использовать стандартный, но у него требования к наименованию
    // в своем провайдере можно более гибко настраивать раздачу ролей и т.п.

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // get email from email and password form:
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        // check if such user exist:
        UserDTO user = userService.getUserByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }
        // check his password:
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Bad credentials");
        }
        // Grand him roles:
        List<GrantedAuthority> authorities = new ArrayList<>();
        String userRole = user.getRoleName();
        authorities.add(new SimpleGrantedAuthority(userRole));
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
