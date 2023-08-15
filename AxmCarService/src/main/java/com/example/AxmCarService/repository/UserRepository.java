package com.example.AxmCarService.repository;

import com.example.AxmCarService.domain.User;

import java.util.Collection;

public interface UserRepository<T extends User> {
    /* Basic CRUD operations */

    T create (T data);
    Collection<T> listAll();
    T update(T data);
    T get(int id);
    Boolean delete(int id);



}
