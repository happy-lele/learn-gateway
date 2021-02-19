package com.muse.gatewayservice.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/say")
    public String say() throws Throwable {
        System.out.println("[spring-cloud-gateway-service]:say Hello!");
        return "[spring-cloud-gateway-service]:say Hello!";
    }

    @GetMapping("/retryRoute")
    public String error() throws Throwable {
        System.out.println("------------------HelloController.retryRoute!------------------");
        throw new RuntimeException();
    }

    @PostMapping("/shout")
    public String shoutHello() {
        System.out.println("[spring-cloud-gateway-service]:shout Hello!");
        return "[spring-cloud-gateway-service]:shout Hello!";
    }

    @GetMapping("/sayParam")
    public String sayParam(HttpServletRequest request, HttpServletResponse response) {
        Enumeration enumeration = request.getParameterNames();
        StringBuffer sb = new StringBuffer();
        if (enumeration.hasMoreElements()) {
            String name = String.valueOf(enumeration.nextElement());
            String value = request.getParameter(name);
            sb.append(" name=" + name);
            sb.append(" value=" + value);
            sb.append(";");
        }
        System.out.println("[spring-cloud-gateway-service]:sayParam Hello! requestParam:" + sb);
        return "[spring-cloud-gateway-service]:sayParam Hello! requestParam:" + sb;
    }
}
