package com.example.AxmCarService.configuration;

import com.example.AxmCarService.filter.CustomAuthenticationFilter;

import com.example.AxmCarService.handler.CustomAccessDeniedHandler;
import com.example.AxmCarService.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final  UserDetailsService userDetailsService;
    private final CustomAuthenticationFilter customAuthenticationFilter;

    private static final String[] PUBLIC_URLS = {"/user/register/**","/user/login/**","/user/verify/code/**","/user/refresh/token/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());

        http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
        http.authorizeHttpRequests(ar-> ar.requestMatchers(PUBLIC_URLS).permitAll());
        http.authorizeHttpRequests(authorizedRequests -> authorizedRequests.requestMatchers(HttpMethod.DELETE,"/user/delete/**").hasAnyAuthority("DELETE:USER"));
        //http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(HttpMethod.DELETE,"/customer/delete/**").hasAnyAuthority("DELETE:CUSTOMER"));
        http.exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint));
        http.authorizeHttpRequests(ar -> ar.anyRequest().authenticated());
        http.addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

                return new ProviderManager(daoAuthenticationProvider);
    }




}
