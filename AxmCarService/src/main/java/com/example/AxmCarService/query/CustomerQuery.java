package com.example.AxmCarService.query;

public class CustomerQuery {

    public static final String INSERT_CUSTOMER_QUERY = "INSERT INTO customer " +
            "(customer_first_name,customer_last_name,customer_email,customer_phone,customer_notes,customer_created_date,customer_created_by)"
            + " VALUES (:firstName,:lastName,:email,:phone,:notes,:createdDate,:createdBy)";

    public static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customers";
}
