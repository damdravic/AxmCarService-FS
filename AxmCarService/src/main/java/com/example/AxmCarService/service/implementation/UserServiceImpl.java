package com.example.AxmCarService.service.implementation;

import com.example.AxmCarService.domain.Role;
import com.example.AxmCarService.domain.User;
import com.example.AxmCarService.dto.domainDTO.UserDTO;
import com.example.AxmCarService.dto.UserDTOMapper;
import com.example.AxmCarService.repository.RoleRepository;
import com.example.AxmCarService.repository.UserRepository;
import com.example.AxmCarService.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.AxmCarService.dto.UserDTOMapper.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

   private final UserRepository<User> userRepository;
   private final RoleRepository<Role> roleRepository;


    @Override
    public UserDTO createUser(User user) {
        return mapToUserDTO(userRepository.create(user));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return mapToUserDTO(userRepository.getUserByEmail(email));
    }

    @Override
    public void sendVerificationCode(UserDTO user) {
        userRepository.sendVerificationCode(user);
    }

    @Override
    public UserDTO verifyCode(String email, String code) {
        return mapToUserDTO(userRepository.verifyCode(email,code));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.getAllUsers().stream().map(this :: mapToUserDTO).collect(Collectors.toList());
    }

    private UserDTO mapToUserDTO(User user){
        log.info("MapToUserDTO from UserServiceImpl");
        return fromUser(user,roleRepository.getRoleByUserId(user.getUserId()));
    }



}
