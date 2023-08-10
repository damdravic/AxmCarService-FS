package com.example.AxmCarService.query;

public class UserQuery {
    public  static final String COUNT_EMAIL_QUERY = "SELECT COUNT(*) FROM Users WHERE email = :email" ;
    public static final String INSERT_USER_QUERY = "INSERT INTO Users (first_name,last_name,email,password) VALUES (:firstName, :lastName, :email, :password )" ;

    public static final String INSERT_ACCOUNT_VERIFICATION_URL_QUERY ="INSERT INTO account_verification (av_user_id,url) VALUE (:userId,:url)" ;
}
