package com.example.AxmCarService.service.implementation;

import com.example.AxmCarService.domain.Role;
import com.example.AxmCarService.repository.RoleRepository;
import com.example.AxmCarService.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository<Role> roleRepository;
    @Override
    public Role getRoleByUserId(Long userId) {
        log.info("getRoleByUserId from RoleServiceImpl ...");
        return roleRepository.getRoleByUserId(userId);
    }
}
