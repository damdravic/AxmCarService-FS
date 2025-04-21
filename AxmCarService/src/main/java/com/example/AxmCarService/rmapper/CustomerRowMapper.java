package com.example.AxmCarService.rmapper;

import com.example.AxmCarService.domain.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Customer.builder()
                .customerId(rs.getLong("customer_id"))
                .firstName(rs.getString("customer_first_name"))
                .lastName(rs.getString("customer_last_name"))
                .email(rs.getString("customer_email"))
                .phone(rs.getString("customer_phone"))
                .notes(rs.getString("customer_notes"))
                .active(rs.getBoolean("customer_active"))
                .createdDate(rs.getDate("customer_created_date"))
                .updateDate(rs.getDate("customer_updated_date"))
                .createdBy(rs.getString("customer_created_by"))
                .updatedBy(rs.getString("customer_updated_by"))
                .build();
    }


}
