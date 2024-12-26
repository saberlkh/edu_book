package com.edu.book.study.aspect;


import org.springframework.stereotype.Component;

@Component
public class UserService {

    public void test(String a, String b) {
        System.out.println("testMethod");
    }

}
