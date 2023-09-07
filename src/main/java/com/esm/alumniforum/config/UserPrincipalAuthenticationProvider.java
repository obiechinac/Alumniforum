package com.esm.alumniforum.config;

import com.esm.alumniforum.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
public class UserPrincipalAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailService userDetailService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails;
        try {
            userDetails = userDetailService.loadUserByUsername(email);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(String.format("User not found with this email: %s", email));
        }
        if (!new BCryptPasswordEncoder().matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

         UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
