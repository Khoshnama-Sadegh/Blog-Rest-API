package com.sadegh.blogrestapi.controller;

import com.sadegh.blogrestapi.entity.Role;
import com.sadegh.blogrestapi.entity.User;
import com.sadegh.blogrestapi.payload.JWTAuthResponse;
import com.sadegh.blogrestapi.payload.LoginDto;
import com.sadegh.blogrestapi.payload.SignUpDto;
import com.sadegh.blogrestapi.repository.RoleRepository;
import com.sadegh.blogrestapi.repository.UserRepository;
import com.sadegh.blogrestapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;



    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse>authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication= authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //get token from tokenProviderClass
        String token=tokenProvider.generateToken(authentication);

        return new ResponseEntity<>(new JWTAuthResponse(token),HttpStatus.OK);

    }



    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        //add a check for username Exist in dataBase

        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!",HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken",HttpStatus.BAD_REQUEST);
        }


        User user=new User();

        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setName(signUpDto.getName());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        Role roles=roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully",HttpStatus.OK);

    }
}
