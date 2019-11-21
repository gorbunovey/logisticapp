package com.gorbunovey.logisticapp;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("musicClassicalBean")
public class ClassicalMusic implements Music {

    @PostConstruct
    public void doMyInit(){
        System.out.println("ClassicalMusic -> Doing my initialization");
    }
    @PreDestroy
    public void doMyDestroy(){
        System.out.println("ClassicalMusic -> Doing my destruction");
    }
    @Override
    public String getSong(){
        return "Hungarian Rhapsody";
    }
}
