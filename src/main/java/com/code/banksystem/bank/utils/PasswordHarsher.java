package com.code.banksystem.bank.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PasswordHarsher {
    public static String hashPassword(String password){
        try{
            // Create MessageDigest Instance for  SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            // Add The password to the digest
            md.update(password.getBytes());
            //Get the hashed Bytes
            byte[] hashedBytes = md.digest();
            //Convert hashed bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes){
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex){
            throw new RuntimeException("Error Hashing the password");
        }
    }

}
