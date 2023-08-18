package com.example.AxmCarService.resource;


import com.example.AxmCarService.domain.HttpResponse;
import com.example.AxmCarService.domain.User;
import com.example.AxmCarService.domainDTO.UserDTO;
import com.example.AxmCarService.form.LoginForm;
import com.example.AxmCarService.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@Data
@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
@Slf4j
public class UserResource {

    private UserService userService;
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody @Valid @NotNull LoginForm loginForm) throws Exception{
        log.info(" in  @PostMapping(\"/login\")");
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(),loginForm.getPassword()));
        log.info(" in  @PostMapping(\"/login\")   **2");
        UserDTO userDTO = userService.getUserByEmail(loginForm.getEmail());
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .data(Map.of("user",userDTO))
                        .message("Login Success")
                        .developerMessage("")
                        .build());




    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> saveUser(@RequestBody @Valid User user){
        UserDTO userDTO = userService.createUser(user);
        return ResponseEntity.created(getUri()).body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .httpStatus(CREATED)
                        .statusCode(CREATED.value())
                        .data(Map.of("user",userDTO))
                        .message("User created")
                        .developerMessage("")
                        .build());
    }

    private URI getUri() {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/get/<userId>").toString());
    }


}
