package com.example.AxmCarService.repository.implementation;

import com.example.AxmCarService.domain.UserPrincipal;
import com.example.AxmCarService.dto.domainDTO.UserDTO;
import com.example.AxmCarService.exception.ApiException;
import com.example.AxmCarService.domain.Role;
import com.example.AxmCarService.domain.User;
import com.example.AxmCarService.repository.RoleRepository;
import com.example.AxmCarService.rmapper.UserRowMapper;
import com.example.AxmCarService.utils.EmailUtils;
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
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.AxmCarService.enumeration.RoleType.ROLE_USER;
import static com.example.AxmCarService.query.UserQuery.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.time.DateFormatUtils.*;
import static org.apache.commons.lang3.time.DateUtils.addDays;


@Repository
@Service
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> ,UserDetailsService {

    private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private  final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final EmailUtils emailUtils;




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
            return user;
        }catch (EmptyResultDataAccessException exception){
            throw new ApiException("EmptyResultDataAccessException");
        }
        catch (Exception exception){
            throw new ApiException(exception.getMessage());
        }


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
                .addValue("password", passwordEncoder.encode(user.getPassword()));

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info(" in loadUserByUsername");
        User user = getUserByEmail(email);
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else {

            Role role = roleRepository.getRoleByUserId((user.getUserId()));
            String permission = role.getPermission();
            return new UserPrincipal(user,permission);
        }
    }

    @Override
     public User getUserByEmail(String email) {
        try{
           return jdbc.queryForObject(SELECT_USER_BY_EMAIL_QUERY,Map.of("email",email),new UserRowMapper());
        }catch(EmptyResultDataAccessException exception){
            throw new ApiException("No User found by email: " + email);

        }catch(DataAccessException exception){
            throw new ApiException( "DataAccessException.");
        }catch(Exception exception){
            throw new ApiException("... exc ...");
        }

    }

    @Override
    public void sendVerificationCode(UserDTO user) {
        String expirationDate = format(addDays(new Date(),1),DATE_FORMAT);
        String verificationCode = randomAlphabetic(8).toUpperCase();


        log.info(expirationDate);

        try{

            jdbc.update(DELETE_CODE_VERIFICATION_BY_USER_ID,Map.of("tfv_user_id",user.getUserId()));
            jdbc.update(INSERT_VERIFICATION_CODE_QUERY,Map.of("tfv_user_id",user.getUserId(),"code",verificationCode,"expiration_date",expirationDate));
            emailUtils.sendMailWithCode(verificationCode,user.getEmail());
        }catch(EmptyResultDataAccessException exception){
            throw new ApiException("No User found by email: ");

        }catch(DataAccessException exception){
            throw new ApiException("DataAccessException." + exception.getMessage());
        }catch(Exception exception){
            throw new ApiException(exception.getMessage());
        }

    }

    @Override
    public User verifyCode(String email, String code) {
        if(!isVerificationCodeExpired(code)) throw new ApiException("This code has expired.Please login again !!");
        try{
            User userByCode = jdbc.queryForObject(SELECT_USER_BY_USER_CODE_QUERY, Map.of("code",code),new UserRowMapper());
            User userByEmail = jdbc.queryForObject(SELECT_USER_BY_EMAIL_QUERY, Map.of("email",email),new UserRowMapper());

            jdbc.update(DELETE_CODE,Map.of("code",code));
            if(userByCode.getEmail().equalsIgnoreCase(userByEmail.getEmail())){
                return userByCode;
            } else {
                throw  new ApiException("Code is invalid .Please try again");
            }}
        catch(EmptyResultDataAccessException exception){
                throw new ApiException("Could not find record");
        }
        catch(Exception e){
   throw  new ApiException("An error is occurred.Please try again." + e.getMessage());
            }

        }

    @Override
    public List<User> getAllUsers() {
        try{

            return jdbc.query(SELECT_ALL_USERS_QUERY,new UserRowMapper());


        }catch(EmptyResultDataAccessException exception){
            throw new ApiException("Could not find record");
        }catch(Exception e){
            throw  new ApiException("An error is occurred when try to get all users." + e.getMessage());
        }
    }

    @Override
    public User getUserById(Long id) {

        try{
            System.out.println("id: " + id);
          User user = jdbc.queryForObject(SELECT_USER_BY_ID_QUERY,Map.of("id",id),new UserRowMapper());
            System.out.println("user: " + user);
            return user;
        }catch (EmptyResultDataAccessException exception){
            throw new ApiException("Could not find record");
    }}

    private boolean isVerificationCodeExpired(String code) {
        try {
            Date expDate = jdbc.queryForObject(SELECT_EXP_DATE, Map.of("code", code), Date.class);
            if(expDate.after(new Date()))
                return true;


        }catch(EmptyResultDataAccessException exception){
            throw new ApiException("Could not find record");
        }
        catch(Exception e){
            throw new ApiException("An error is occurred.Please try again." + e.getMessage());
        }

   return false;
    }

}

