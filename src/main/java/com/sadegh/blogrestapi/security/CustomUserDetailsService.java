package com.sadegh.blogrestapi.security;

import com.sadegh.blogrestapi.entity.Role;
import com.sadegh.blogrestapi.entity.User;
import com.sadegh.blogrestapi.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user=userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail)
                .orElseThrow(()->new UsernameNotFoundException
                        ("User not found with username or email: "+usernameOrEmail));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),mapRolesToGrandAuthority(user.getRoles()));


    }



    private Collection<? extends GrantedAuthority>mapRolesToGrandAuthority(Set<Role> roles){

        return roles.stream().map(role -> (new SimpleGrantedAuthority(role.getName()))).collect(Collectors.toList());
    }
}
