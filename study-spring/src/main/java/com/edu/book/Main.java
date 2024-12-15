package com.edu.book;

import com.spring.StudyApplication;
import com.study.service.AppConfig;
import com.study.service.UserService;

public class Main {

    public static void main(String[] args) {
        StudyApplication application = new StudyApplication(AppConfig.class);
        UserService userService = (UserService) application.getBean("userService");
        userService.test();
    }

}