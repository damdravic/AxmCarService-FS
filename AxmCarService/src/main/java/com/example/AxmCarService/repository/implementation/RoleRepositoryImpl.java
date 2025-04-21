package com.example.AxmCarService.repository.implementation;

import com.example.AxmCarService.domain.Role;
import com.example.AxmCarService.exception.ApiException;
import com.example.AxmCarService.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.AxmCarService.rmapper.RoleRowMapper;

import java.util.Collection;

import static com.example.AxmCarService.enumeration.RoleType.ROLE_USER;
import static com.example.AxmCarService.query.RoleQuery.*;
import static java.util.Map.*;


@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImpl implements RoleRepository {



    private final NamedParameterJdbcTemplate jdbc;

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

        //TODO here for first time does not have role name in roles table  --fix this

        try{
            Role role = jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY, of("roleName",roleName),new RoleRowMapper());
            jdbc.update(INSERT_ROLE_TO_USER_QUERY, of("userId",userId,"roleId",role.getRoleId()));


        }catch(EmptyResultDataAccessException exception){
            throw new ApiException("   Role not exist");
        }catch ( DataAccessException exception){
            throw new ApiException(" DataAccessException");
        }

    }

    int x =1;
    @Override
    public Role getRoleByUserId(Long userId) {
        try {

            Role role = jdbc.queryForObject(SELECT_ROLE_BY_ID_QUERY, of("id", userId), new RoleRowMapper());
            return role;
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No role found by name: " + ROLE_USER.name());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public Role getRoleByUserEmail(String email) {
        return null;
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {

    }}