package com.example.AxmCarService.query;

public class UserQuery {
    public static final String COUNT_EMAIL_QUERY = "SELECT COUNT(*) FROM users WHERE email = :email";
    public static final String INSERT_USER_QUERY = "INSERT INTO Users(first_name,last_name,email,password) VALUES (:firstName,:lastName,:email,:password)";
    public static final String INSERT_ACCOUNT_VERIFICATION_QUERY = "INSERT INTO verification_account (va_user_id,url) VALUES (:userId,:url)";

    public static final String SELECT_USER_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email = :email";
    public static final String DELETE_CODE_VERIFICATION_BY_USER_ID = "DELETE FROM two_factor_verifications WHERE tfv_user_id =:tfv_user_id";
    public static final String  INSERT_VERIFICATION_CODE_QUERY ="INSERT INTO two_factor_verifications (tfv_user_id, code, expiration_date) VALUES" +
            " (:tfv_user_id, :code, :expiration_date)";
}