package com.edu.book.study.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserServiceAspectJ {

    @Before(value = "execution(public void com.edu.book.study.aspect.UserService.test(..)) && args(a, b)", argNames = "a, b")
    public void before(String a, String b) {
        System.out.println(a);
        System.out.println(b);
        System.out.println("before");
    }

}
