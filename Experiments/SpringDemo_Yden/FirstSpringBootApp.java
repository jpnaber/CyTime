package com.sample.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Scanner;

@RestController
@RequestMapping("/Yden")
@SpringBootApplication
public class FirstSpringBootApp {

    @GetMapping
    public String getName(){
        System.out.println("What's your name? Enter here: ");
        Scanner scan = new Scanner(System.in);
        String name = scan.next();
        String message = "Well hello and welcome, " + name + "!";
        return message;
    }
}
