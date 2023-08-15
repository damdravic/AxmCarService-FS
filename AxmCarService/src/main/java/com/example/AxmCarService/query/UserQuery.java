package com.example.AxmCarService.query;

public class UserQuery {
    public  static final String COUNT_EMAIL_QUERY = "SELECT COUNT(*) FROM Users WHERE email = :email";
    public static final String INSERT_USER_QUERY = "INSERT INTO Users(first_name,last_name,email,password) VALUE (:firstName,:lastName,:email,:password)" ;
   public static final String INSERT_ACCOUNT_VERIFICATION_QUERY = "INSERT INTO verification_account (va_user_id,url) VALUES (:userId,:url); ";

}
