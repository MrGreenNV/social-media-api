package ru.averkiev.socialmediaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс для запуска приложения.
 */
@SpringBootApplication
public class SocialMediaApiApplication {

    /**
     * Основной метод для старта приложения.
     * @param args параметры запуска.
     */
    public static void main(String[] args) {
        SpringApplication.run(SocialMediaApiApplication.class, args);
    }

}
