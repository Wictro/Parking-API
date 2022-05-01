package com.wictro.cacttus.backend.controller;

import com.wictro.cacttus.backend.config.JwtTokenUtil;
import com.wictro.cacttus.backend.dto.auth.LoginRequest;
import com.wictro.cacttus.backend.dto.auth.LoginResponse;
import com.wictro.cacttus.backend.dto.auth.RegisterRequest;
import com.wictro.cacttus.backend.dto.auth.RegisterResponse;
import com.wictro.cacttus.backend.dto.http.ErrorResponse;
import com.wictro.cacttus.backend.dto.http.GenericJsonResponse;
import com.wictro.cacttus.backend.exception.UserWithEmailAlreadyExistsException;
import com.wictro.cacttus.backend.exception.UserWithUsernameAlreadyExistsException;
import com.wictro.cacttus.backend.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/login")
    public GenericJsonResponse<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest, HttpServletResponse response) throws Exception {
        try{
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        }
        catch (BadCredentialsException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getMessage()));
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return new GenericJsonResponse<>(true, new LoginResponse(token));
    }

    @PostMapping("/register")
    public GenericJsonResponse<?> saveUser(@RequestBody RegisterRequest user, HttpServletResponse response){
        try {
            userDetailsService.save(user);
        }
        catch (UserWithUsernameAlreadyExistsException | UserWithEmailAlreadyExistsException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new GenericJsonResponse<ErrorResponse>(false, new ErrorResponse(e.getMessage()));
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
        return new GenericJsonResponse<RegisterResponse>(true, null);
    }

    private void authenticate(String username, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
