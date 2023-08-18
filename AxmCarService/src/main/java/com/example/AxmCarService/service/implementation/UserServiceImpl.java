package com.example.AxmCarService.service.implementation;

import com.example.AxmCarService.domain.User;
import com.example.AxmCarService.domainDTO.UserDTO;
import com.example.AxmCarService.dto.UserDTOMapper;
import com.example.AxmCarService.repository.UserRepository;
import com.example.AxmCarService.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
   @Autowired
   private UserRepository<User> userRepository;


    @Override
    public UserDTO createUser(User user) {
        return UserDTOMapper.fromUser(userRepository.create(user));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return UserDTOMapper.fromUser(userRepository.getUserByEmail(email));
    }
}
