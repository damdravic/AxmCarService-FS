package com.example.AxmCarService.resource;


import com.example.AxmCarService.domain.HttpResponse;
import com.example.AxmCarService.domain.User;
import com.example.AxmCarService.domain.UserPrincipal;
import com.example.AxmCarService.dto.domainDTO.UserDTO;
import com.example.AxmCarService.form.LoginForm;
import com.example.AxmCarService.provider.TokenProvider;
import com.example.AxmCarService.service.RoleService;
import com.example.AxmCarService.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.io.File;
import java.net.URI;
import java.util.Map;

import static com.example.AxmCarService.dto.UserDTOMapper.toUser;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;

@Data
@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserResource {

    private static final String TOKEN_PREFIX = "Bearer ";
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
   private final RoleService roleService;
    private TokenProvider tokenProvider;


    @PostMapping("/login" )
    public ResponseEntity<HttpResponse> login(@RequestBody @Valid  LoginForm loginForm) {

         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(),loginForm.getPassword()));
        UserDTO user = userService.getUserByEmail(loginForm.getEmail());

        return user.isUsingMfa()?  sendVerificationCode(user) :  sendResponse(user);


    }
    @GetMapping("/refresh/token")
    public ResponseEntity<HttpResponse> refreshToken(HttpServletRequest request){
        log.info("in refresh token");
        if(isHeaderAndTokenValid(request)){
            log.info("in refresh token step 2");
            String token = request.getHeader(AUTHORIZATION).substring(TOKEN_PREFIX.length());
            UserDTO user = userService.getUserByEmail(tokenProvider.getSubject(token,request));
            return ResponseEntity.ok().body(
                    HttpResponse.builder()
                            .timeStamp(now().toString())
                            .httpStatus(OK)
                            .statusCode(OK.value())
                            .data(Map.of("user",user,
                                    "accessToken",tokenProvider.createAccessToken(getUserPrincipal(user)),
                                    "refreshToken", tokenProvider.createRefreshToken(getUserPrincipal(user))))
                            .message("Token Refreshed")
                            .developerMessage("")
                            .build());
        }
        return ResponseEntity.badRequest().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .reason("Refresh Token missing or invalid")
                        .message("Token Refreshed")
                        .developerMessage("Refresh Token missing or invalid")
                        .build());

    }

    private boolean isHeaderAndTokenValid(HttpServletRequest request) {
        log.info(request.getHeader(AUTHORIZATION));
        return request.getHeader(AUTHORIZATION) != null && request.getHeader(AUTHORIZATION).startsWith(TOKEN_PREFIX)
                && tokenProvider.isTokenValid(tokenProvider.getSubject(request.getHeader(AUTHORIZATION).substring(TOKEN_PREFIX.length()),request),
                request.getHeader(AUTHORIZATION).substring(TOKEN_PREFIX.length()));
    }


    @PostMapping("/register")
    public ResponseEntity<HttpResponse> saveUser(@RequestBody @Valid User user){
        System.out.println("in saveUser" + user);
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

    @GetMapping("/all")
    public ResponseEntity<HttpResponse> getAllUsers(){
        System.out.println("in getAllUsers");
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .data(Map.of("users",userService.getAllUsers()))
                        .message("Login Success")
                        .developerMessage("")
                        .build());
    }


    /* fake data   -will be removed */
    public static User[] getFakeUsers(){
        User[] users = new User[3];
        users[0] = new User(1L, "John", "Doe", "john.doe@example.com", "password123", "123 Main St", "555-1234", "Mr.", "Bio of John", "url/to/image", true, true, false, now());
        users[1] = new User(2L, "Jane", "Smith", "jane.smith@example.com", "password456", "456 Elm St", "555-5678", "Ms.", "Bio of Jane", "url/to/image", true, true, false, now());
        users[2] = new User(3L, "Alice", "Johnson", "alice.johnson@example.com", "password789", "789 Oak St", "555-9012", "Dr.", "Bio of Alice", "url/to/image", true, true, false, now());
        return users;
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
                        .data(Map.of("user",user,
                                        "accessToken",tokenProvider.createAccessToken(getUserPrincipal(user)),
                                        "refreshToken", tokenProvider.createRefreshToken(getUserPrincipal(user))))
                        .message("Login Success")
                        .developerMessage("")
                        .build());
    }

    private UserPrincipal getUserPrincipal(UserDTO user) {
        log.info("from method getUserPrincipal for token and refreshToken");
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
