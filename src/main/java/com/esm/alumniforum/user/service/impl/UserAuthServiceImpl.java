package com.esm.alumniforum.user.service.impl;

import com.esm.alumniforum.common.response.ApiResponse;
import com.esm.alumniforum.config.UserPrincipalAuthenticationProvider;
import com.esm.alumniforum.exceptions.LoginException;
import com.esm.alumniforum.exceptions.ResourceNotFoundException;
import com.esm.alumniforum.security.JwtTokenUtil;
import com.esm.alumniforum.user.dto.LoginForm;
import com.esm.alumniforum.user.dto.UsersResponse;
import com.esm.alumniforum.user.model.Users;
import com.esm.alumniforum.user.repository.UsersRepository;
import com.esm.alumniforum.user.service.interfa.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class UserAuthServiceImpl implements UserAuthService {


    @Autowired
    private DaoAuthenticationProvider authenticationProvider;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Object login(LoginForm form) {
        Users users = usersRepository.findByEmail(form.getEmail()).orElseThrow(()-> new ResourceNotFoundException("User not found","email",form.getEmail()));
        UsersResponse usersResponse = UsersResponse.builder()
                .email(users.getEmail())
                .id(users.getId())
                .roles(users.getRoles())
                .build();
        if(isValidLogin( form.getPassword(),users)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword());
            Authentication auth = authenticationProvider.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            Map<String, Object> data = new HashMap<>();
            Map<String, String> jwt = jwtTokenUtil.generateToken(auth);
            data.put("tokens", jwt);
            data.put("user", usersResponse);
            return data;
        }
        else {
            return new LoginException("Invalid Username Or Password");
        }
    }

//    @Override
//    public Map<String, Object> login(LoginForm form) {
//        Users users = usersRepository.findByEmail(form.getEmail()).orElseThrow(
//                () -> new ResourceNotFoundException("User not found", "email", form.getEmail()));
//        UsersResponse usersResponse = UsersResponse.builder()
//                .email(users.getEmail())
//                .id(users.getId())
//                .roles(users.getRoles())
//                .build();
//        if (isValidLogin(form.getPassword(), users)) {
//            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                    new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword());
//            Authentication auth = authenticationProvider.authenticate(usernamePasswordAuthenticationToken);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//            Map<String, Object> data = new HashMap<>();
//            Map<String, String> jwt = jwtTokenUtil.generateToken(auth);
//            data.put("tokens", jwt);
//            data.put("user", usersResponse);
//            return data;
//        } else {
//            throw new LoginException("Invalid Username Or Password");
//        }
//    }

    private boolean isValidLogin(String password,Users users ) {
        System.out.println(users.getPassword());
        return passwordEncoder.matches(password, users.getPassword());
    }
}
