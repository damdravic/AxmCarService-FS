package com.example.AxmCarService.service;

import com.example.AxmCarService.domain.User;
import com.example.AxmCarService.domainDTO.UserDTO;

public interface UserService {

    public UserDTO createUser(User user);
      public UserDTO getUserByEmail(String email);

}
