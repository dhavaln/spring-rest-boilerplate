/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jumpbyte.test.springrest.service;

import javax.servlet.http.HttpSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 *
 * @author dhavalnagar
 */
@Aspect
public class Secure {

    @Before("execution(* se.sawano..*.*(..))")
    public void checkLogin(JoinPoint joinPoint) {
        System.out.println("ASPECT: Checking login, Args: " + joinPoint.getArgs().length);
        
        HttpSession s = (HttpSession) RequestContextHolder
                    .currentRequestAttributes()
                    .resolveReference(RequestAttributes.REFERENCE_SESSION);
        
        if(s != null){
            System.out.println("ASPECT: Session found");            
        }
    }
}
