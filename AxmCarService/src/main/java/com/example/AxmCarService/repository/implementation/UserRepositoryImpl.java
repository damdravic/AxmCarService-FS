package com.example.AxmCarService.repository.implementation;

import com.example.AxmCarService.domain.UserPrincipal;
import com.example.AxmCarService.exception.ApiException;
import com.example.AxmCarService.domain.Role;
import com.example.AxmCarService.domain.User;
import com.example.AxmCarService.repository.RoleRepository;
import com.example.AxmCarService.rowmapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.AxmCarService.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import static com.example.AxmCarService.enumeration.RoleType.ROLE_USER;
import static com.example.AxmCarService.query.UserQuery.*;


@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> , UserDetailsService {

    private  final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public User create(User user) {

        //Check if email is unique
        if(checkUserEmail(user.getEmail().trim().toLowerCase()) > 0) throw new ApiException("Email exist" );
        //save new user
        try{
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameter = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY,parameter,holder);
            user.setUserId(Objects.requireNonNull(holder.getKey()).longValue());
            log.info("create user 1");
            roleRepository.addRoleToUser(user.getUserId(),ROLE_USER.name());
            log.info("create user 2");
        }catch (EmptyResultDataAccessException exception){
            throw new ApiException("EmptyResultDataAccessException");
        }
        catch (Exception exception){
            throw new ApiException("something else");
        }

        return user; //this is not correct
    }



    @Override
    public Collection listAll() {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public Boolean delete(int id) {
        return null;
    }

    private Integer checkUserEmail(String email) {
        return jdbc.queryForObject(COUNT_EMAIL_QUERY, Map.of("email",email),Integer.class );
    }

    private SqlParameterSource getSqlParameterSource(User user) {

        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", passwordEncoder.encode(user.getLastName()));

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info(" in loadUserByUsername");
        User user = getUserByEmail(email);
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else {
            log.info("User found in the database:{}",user);
            return new UserPrincipal(user,roleRepository.getRoleByUserId(user.getUserId()).getPermission());
        }
    }

    public User getUserByEmail(String email) {
        try{
            log.info(" in getUserByMail");
           return jdbc.queryForObject(SELECT_USER_BY_EMAIL_QUERY,Map.of("email","email"),new UserRowMapper());
        }catch(EmptyResultDataAccessException exception){
            throw new ApiException("No User found by email: " + email);

        }catch(DataAccessException exception){
            throw new ApiException("DataAccessException.");
        }catch(Exception exception){
            throw new ApiException("... exc ...");
        }

    }
}
