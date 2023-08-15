package com.example.AxmCarService.query;

public class RoleQuery {
    public  static final String SELECT_ROLE_BY_NAME_QUERY ="SELECT * FROM roles WHERE role_name =: roleName" ;
    public static final String INSERT_ROLE_TO_USER_QUERY = "INSERT  INTO UserRole (ur_user_id,ur_role_id) VALUE ()";
}
