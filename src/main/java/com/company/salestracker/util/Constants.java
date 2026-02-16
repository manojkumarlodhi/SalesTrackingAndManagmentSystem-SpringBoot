package com.company.salestracker.util;

public class Constants {

    // Validation regex patterns
    public static final String PHONE_REGEX = "\\d{10,15}";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    // Validation messages
    public static final String NAME_REQUIRED = "Name is required";
    public static final String NAME_MAX = "Name must be at most 50 characters";
    public static final String EMAIL_REQUIRED = "Email is required";
    public static final String EMAIL_VALID = "Email should be valid";
    public static final String PHONE_REQUIRED = "Phone is required";
    public static final String PHONE_VALID = "Phone must be numeric and 10 to 15 digits";
    public static final String PASSWORD_REQUIRED = "Password is required";
    public static final String PASSWORD_VALID = "Password must be minimum 8 characters, with at least 1 uppercase, 1 lowercase, 1 number and 1 special character";
    public static final String ROLE_REQUIRED = "Roles cannot be empty";

}
