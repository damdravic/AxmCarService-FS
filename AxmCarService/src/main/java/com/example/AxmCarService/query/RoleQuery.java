package com.example.AxmCarService.query;

public class RoleQuery {
    public  static final String SELECT_ROLE_BY_NAME_QUERY ="SELECT * FROM roles WHERE role_name = :roleName" ;
    public static final String INSERT_ROLE_TO_USER_QUERY = "INSERT  INTO user_roles (ur_user_id,ur_role_id) VALUES (:userId,:roleId)";
    public static final String SELECT_ROLE_BY_ID_QUERY = "SELECT r.role_id, r.role_name, r.permission FROM roles r JOIN user_roles ur ON ur.ur_role_id = r.role_id JOIN users u ON u.user_id = ur.ur_user_id WHERE u.user_id = :id";

}
