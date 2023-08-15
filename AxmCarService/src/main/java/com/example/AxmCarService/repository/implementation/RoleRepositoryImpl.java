package com.example.AxmCarService.repository.implementation;

import com.example.AxmCarService.domain.Role;
import com.example.AxmCarService.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@AllArgsConstructor

public class RoleRepositoryImpl implements RoleRepository {
    @Override
    public Role create(Role data) {
        return null;
    }

    @Override
    public Collection listAll() {
        return null;
    }

    @Override
    public Role update(Role data) {
        return null;
    }

    @Override
    public Role get(int id) {
        return null;
    }

    @Override
    public Boolean delete(int id) {
        return null;
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {

    }
}
