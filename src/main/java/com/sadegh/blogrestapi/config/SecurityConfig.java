package com.sadegh.blogrestapi.config;

import com.sadegh.blogrestapi.security.CustomUserDetailsService;
import com.sadegh.blogrestapi.security.JwtAuthenticationEntryPoint;
import com.sadegh.blogrestapi.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());


    }



    @Bean
    public JwtAuthenticationFilter authenticationFilter(){
        return new JwtAuthenticationFilter();
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }






    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest()
                .authenticated();
        http.addFilterBefore(authenticationFilter(),UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    //    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//
//        UserDetails sadegh=User.builder().username("sadegh").password(passwordEncoder().encode("sadegh")).roles("ADMIN").build();
//        UserDetails ali=User.builder().username("ali").password(passwordEncoder().encode("ali")).roles("USER").build();
//
//        return new InMemoryUserDetailsManager(sadegh,ali);
//    }
}
