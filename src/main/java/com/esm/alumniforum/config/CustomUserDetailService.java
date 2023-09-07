package com.esm.alumniforum.config;

import com.esm.alumniforum.security.UserPrincipal;
import com.esm.alumniforum.user.model.Users;
import com.esm.alumniforum.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {
        private final UsersRepository userRepository;

        @Autowired
        public CustomUserDetailService(UsersRepository userRepository) {
            this.userRepository = userRepository;
        }


    @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            Users user = userRepository.findByEmail(email).orElseThrow(()->
            new UsernameNotFoundException(String.format("User not found with this email: %s", email)));

            return UserPrincipal.create(user);
        }

    public UserPrincipal findByEmail(String email){
        Users appUser = userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException(String.format("User not found with this email: %s", email))
        );
        return UserPrincipal.create(appUser);
    }

    public UserDetails loadUserById(String id){
        Users appUser = userRepository.findById(id).orElseThrow(
                ()-> new UsernameNotFoundException("User not found"));
        return UserPrincipal.create(appUser);
    }

    }


