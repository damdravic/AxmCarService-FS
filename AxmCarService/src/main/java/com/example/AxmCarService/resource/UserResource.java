package com.example.AxmCarService.resource;


import com.example.AxmCarService.domain.HttpResponse;
import com.example.AxmCarService.domain.User;
import com.example.AxmCarService.domain.UserPrincipal;
import com.example.AxmCarService.dto.domainDTO.UserDTO;
import com.example.AxmCarService.form.LoginForm;
import com.example.AxmCarService.provider.TokenProvider;
import com.example.AxmCarService.service.RoleService;
import com.example.AxmCarService.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.net.URI;
import java.util.Map;

import static com.example.AxmCarService.dto.UserDTOMapper.toUser;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@Data
@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserResource {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
   private final RoleService roleService;
    private TokenProvider tokenProvider;


    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody @Valid @NotNull LoginForm loginForm) throws Exception{

         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(),loginForm.getPassword()));

        UserDTO user = userService.getUserByEmail(loginForm.getEmail());

        return user.isUsingMfa()?  sendVerificationCode(user) :  sendResponse(user);

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





    @GetMapping("/profile")
    public ResponseEntity<HttpResponse> profile(Authentication authentication){

        System.out.println("dddddddd");
        UserDTO user = userService.getUserByEmail(authentication.getName());
        System.out.println(authentication.getPrincipal());

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .data(Map.of("user",user ))
                        .message("profile Retrieved")
                        .statusCode(OK.value())
                        .build());
    }


    @GetMapping("/verify/code/{email}/{code}")
    public ResponseEntity<HttpResponse> verifyCode(@PathVariable ("email") String email,
                                                   @PathVariable ("code") String code ){

        UserDTO user = userService.verifyCode(email,code);

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .data(Map.of("user",user ,"access_token",tokenProvider.createAccessToken(getUserPrincipal(user)),"refreshToken",
                                tokenProvider.createRefreshToken(getUserPrincipal(user))))
                        .message("Login Success")
                        .developerMessage("")
                        .build());
    }


    private URI getUri() {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/get/<userId>").toString());
    }



    private ResponseEntity<HttpResponse> sendResponse(UserDTO user) {

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .data(Map.of("user",user ,"access_token",tokenProvider.createAccessToken(getUserPrincipal(user)),"refreshToken",
                                tokenProvider.createRefreshToken(getUserPrincipal(user))))
                        .message("Login Success")
                        .developerMessage("")
                        .build());
    }

    private UserPrincipal getUserPrincipal(UserDTO user) {
     return new UserPrincipal(toUser(userService.getUserByEmail(user.getEmail())),roleService.getRoleByUserId(user.getUserId()).getPermission());

    }

    private ResponseEntity<HttpResponse> sendVerificationCode(UserDTO user) {
        log.info("in sendVerificationCode");

        userService.sendVerificationCode(user);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .data(Map.of("user",user))
                        .message("Verification Code Sent")
                        .developerMessage("")
                        .build());
    }


}
