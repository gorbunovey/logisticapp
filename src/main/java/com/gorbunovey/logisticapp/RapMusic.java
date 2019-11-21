package com.gorbunovey.logisticapp;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("musicRapBean")
public class RapMusic implements Music {
    public static RapMusic getRapMusic(){
        return new RapMusic();
    }
    private RapMusic(){}
    @PostConstruct
    public void doMyInit(){
        System.out.println("RapMusic -> Doing my initialization");
    }
    @PreDestroy
    public void doMyDestroy(){
        System.out.println("RapMusic -> Doing my destruction");
    }
    @Override
    public String getSong() {
        return "50 cent - Candy shop";
    }
}
