package com.example.AxmCarService.repository.implementation;

import com.example.AxmCarService.domain.Customer;
import com.example.AxmCarService.domain.User;
import com.example.AxmCarService.domain.UserPrincipal;
import com.example.AxmCarService.dto.domainDTO.UserDTO;
import com.example.AxmCarService.exception.ApiException;
import com.example.AxmCarService.repository.CustomerRepository;
import com.example.AxmCarService.rmapper.CustomerRowMapper;
import com.example.AxmCarService.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static com.example.AxmCarService.query.CustomerQuery.*;

@Repository
@Data
@Slf4j
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    public final NamedParameterJdbcTemplate jdbc;
    public final UserService userService;







    @Override
    public Customer create(Customer customer) {
        KeyHolder kh = new GeneratedKeyHolder();
        SqlParameterSource params = getSqlParameterSource(customer);
        try{
            jdbc.update(INSERT_CUSTOMER_QUERY,params,kh);
        }catch (BadSqlGrammarException exception){
            throw new BadSqlGrammarException(exception.getSql(),exception.getSql(),exception.getSQLException());
        }catch (Exception e){
            throw new ApiException(e.getMessage()+ " "+ "Error while creating customer");
        }
        customer.setCustomerId(kh.getKey().longValue());
        log.info("Customer created successfully");
    return customer;

    }

    private SqlParameterSource getSqlParameterSource(Customer customer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("firstName",customer.getFirstName())
                .addValue("lastName",customer.getLastName())
                .addValue("email",customer.getEmail())
                .addValue("phone",customer.getPhone())
                .addValue("notes",customer.getNotes())
                .addValue("createdDate",customer.getCreatedDate())
                .addValue("createdBy",getUserId(authentication));
        return params;
    }

    private Long getUserId(Authentication authentication) {

        UserDTO user = userService.getUserByEmail(authentication.getName());


        return user.getUserId();
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }




    public void delete(Long id) {
        jdbc.update(DELETE_CUSTOMER_BY_ID_QUERY, Map.of("customerId", id));
    }

    public List<Customer> getAll() {
      List<Customer> customers =   jdbc.query(SELECT_ALL_CUSTOMERS, new CustomerRowMapper());

      //get name of user by id in filed createdBy
      customers.forEach(customer -> customer.setCreatedBy(getUserName(customer.getCreatedBy())));


        return customers;
    }

   //get name of user by id
    private String getUserName(String userId){
        System.out.println(userId);
try{
    System.out.println(userId);
        Long userIdLong = Long.parseLong(userId);
    System.out.println(userIdLong);

        List<UserDTO> usersDTO = new ArrayList<>();
        System.out.println("pass1");
        for(UserDTO user : usersDTO){
            if(user.getUserId().equals(userIdLong)){
                return user.getFirstName();
            }
        }
        System.out.println("pass2");

        UserDTO userBD = userService.getUserById(userIdLong);
        usersDTO.add(userBD);
        System.out.println(userBD.toString());
        return userBD.getFirstName();}
catch (Exception e){
    log.error("Error while getting user name " + e.getMessage());
    return "User not found";
}
    }

    public Customer getCustomerById(Long id) {
        try{
            return jdbc.queryForObject(SELECT_CUSTOMER_BY_ID_QUERY,Map.of("customerId",id),new CustomerRowMapper());
        }catch (Exception e){
            throw new ApiException("Customer not found");
}
    }
}
