package com.example.AxmCarService.repository;

import com.example.AxmCarService.domain.User;
import com.example.AxmCarService.dto.domainDTO.UserDTO;

import java.util.Collection;
import java.util.List;

public interface UserRepository<T extends User> {
    /* Basic CRUD operations */

    T create (T data);
    Collection<T> listAll();
    T update(T data);
    T get(int id);
    Boolean delete(int id);

    /* More complex operations */

    User getUserByEmail(String email);
    void sendVerificationCode(UserDTO user);

    User verifyCode(String email, String code);

    List<T> getAllUsers();

    User getUserById(Long id);
}
