package com.bankmisr.onlinebookstore.constant;

public class AppConstants {
    private AppConstants() {throw new IllegalStateException("AppConstants class are not meant to be instantiated.");}
    public static final String CONTENT_TYPE = "application/json";
    public static final long EXPIRATION_TIME = 3600000L;
    public static final String SECRET = "ThisIsASecret";
    public static final String ACCESS_CONTROL_EXPOSE = "access-control-expose-headers";
    public static final String DASH = "-";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String INVALID_FIELD = "Invalid field ";

    public static final String BORROW_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String INVALID_USER_NAME = "UserName can not be empty";
    public static final String INVALID_PASWORD = "Password can not be empty";
    public static final String INVALID_EMAIL = "Email can not be empty";
    public static final String INVALID_FIRSTNAME = "FirstName can not be empty";
    public static final String INVALID_LASTNAME = "LastName can not be empty";
    public static final String INVALID_ROLE = "Role can not be empty";

    public static final String USER_NOT_EXIST = "User not exist";
}
