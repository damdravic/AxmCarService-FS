package com.example.AxmCarService.query;

public class CustomerQuery {

    public static final String INSERT_CUSTOMER_QUERY = "INSERT INTO customer " +
            "(customer_first_name,customer_last_name,customer_email,customer_phone,customer_notes,customer_created_date,customer_created_by)"
            + " VALUES (:firstName,:lastName,:email,:phone,:notes,:createdDate,:createdBy)";

    public static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customer";

    public static final String SELECT_CUSTOMER_BY_ID_QUERY = "SELECT * FROM customer WHERE customer_id = :customerId";
    public static final String DELETE_CUSTOMER_BY_ID_QUERY = "DELETE FROM customer WHERE customer_id = :customerId " ;
}
