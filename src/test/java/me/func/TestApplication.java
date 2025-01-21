package me.func;

import org.springframework.boot.SpringApplication;

public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[]{Application.class, TestcontainersConfiguration.class}, args);
    }

}
