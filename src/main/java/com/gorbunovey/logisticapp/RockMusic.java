package com.gorbunovey.logisticapp;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("musicRockBean")
public class RockMusic implements Music {
    @PostConstruct
    public void doMyInit(){
        System.out.println("RockMusic -> Doing my initialization");
    }
    @PreDestroy
    public void doMyDestroy(){
        System.out.println("RockMusic -> Doing my destruction");
    }
    @Override
    public String getSong() {
        return "Highway to hell";
    }
}
