package edu.xinan.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * WebFlux 还支持基于 lambda 表达式的函数式编程模型
 * 目前 Spring Boot 不支持在一个应用中同时使用两种不同的编程模式
 * @author hoaven
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

