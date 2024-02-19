package com.code.banksystem.bank.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterValidator {
    public static boolean isValidPassword(String password){
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidName(String name){
        String regex = "^[A-Z][a-zA-Z]{3,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email){
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber){
        String localPattern = "^07[238]\\d{7}$";
        String internationalPattern = "^\\+2507[238]\\d{7}";
        return phoneNumber.matches(localPattern) || phoneNumber.matches(internationalPattern);
    }

    public static List<String> validatePassword(String password){
        List<String> errorMessages = new ArrayList<>();
        //Check if at least 8 characters
        if(password.length() < 8)
            errorMessages.add("Password must be at least 8 characters long");
        if(!password.matches(".*\\d.*"))
            errorMessages.add("Password must contain at least one digit");
        if(!password.matches(".*[a-z].*"))
            errorMessages.add("Password must contain at least one lowercase letter");
        if(!password.matches(".*[A-Z].*"))
            errorMessages.add("Password must contain at least one capital letter");
        if(!password.matches(".*[@#$%^&+=].*"))
            errorMessages.add("Password must contain at least one special character");
        return errorMessages;
    }
}
