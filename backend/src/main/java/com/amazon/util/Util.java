package com.amazon.util;

import com.amazon.entity.CurrentUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Util {

    public static void main(String[] args) {
        passwordGenerator("123456");
    }

    public static void passwordGenerator(String plainTextPassword) {
        System.out.println(new BCryptPasswordEncoder().encode(plainTextPassword));
    }

    public static CurrentUser getCurrentUser(){
        return (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
