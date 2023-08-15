package com.example.AxmCarService.configuration;

import com.example.AxmCarService.handler.CustomAccessDeniedHandeler;
import com.example.AxmCarService.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private BCryptPasswordEncoder passwordEncoder;
    private CustomAccessDeniedHandeler customAccessDeniedHandeler;
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private static final String[] PUBLIC_URLS = {"/user/login/**","/user/register/**"} ;

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{

      http.csrf(AbstractHttpConfigurer :: disable) .cors(AbstractHttpConfigurer :: disable);

        http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
        http.authorizeHttpRequests(authorizeRequest -> authorizeRequest.requestMatchers( PUBLIC_URLS).permitAll());
        http.authorizeHttpRequests(authorizedRequests -> authorizedRequests.requestMatchers(HttpMethod.DELETE,"/user/delete/**").hasAnyAuthority("DELETE:USER"));
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(HttpMethod.DELETE,"/customer/delete/**").hasAnyAuthority("DELETE:CUSTOMER"));
        http.exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(customAccessDeniedHandeler).authenticationEntryPoint(customAuthenticationEntryPoint));
        http.authorizeHttpRequests(ar -> ar.anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(null);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
                return new ProviderManager(daoAuthenticationProvider);
    }




}
