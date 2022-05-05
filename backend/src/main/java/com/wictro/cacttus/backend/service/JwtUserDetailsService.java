package com.wictro.cacttus.backend.service;

import com.wictro.cacttus.backend.dto.auth.RegisterRequest;
import com.wictro.cacttus.backend.exception.UserWithEmailAlreadyExistsException;
import com.wictro.cacttus.backend.exception.UserWithUsernameAlreadyExistsException;
import com.wictro.cacttus.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.wictro.cacttus.backend.model.User user = userRepository.getUserByUsername(username);

        if(user != null){
            ArrayList<SimpleGrantedAuthority> role = new ArrayList();
            role.add(new SimpleGrantedAuthority(user.getRole()));
            return new User(user.getUsername(), user.getPassword(), role);
        }
        else {
           throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public void save(RegisterRequest user) throws UserWithUsernameAlreadyExistsException, UserWithEmailAlreadyExistsException {
        if(userRepository.getUserByUsername(user.getUsername()) != null){
            throw new UserWithUsernameAlreadyExistsException();
        }

        if(userRepository.getUserByEmail(user.getEmail()) != null){
            throw new UserWithEmailAlreadyExistsException();
        }

        com.wictro.cacttus.backend.model.User newUser = new com.wictro.cacttus.backend.model.User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setRole("USER");
        userRepository.save(newUser);
    }
}
