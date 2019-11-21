package com.gorbunovey.logisticapp;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Random;

@Component
public class RockMusic implements Music {

    String[] songs = {"Highway to hell", "Wind of change", "We will rock you"};

    @PostConstruct
    public void doMyInit(){
        System.out.println("RockMusic -> Doing my initialization");
    }
    @PreDestroy
    public void doMyDestroy(){
        System.out.println("RockMusic -> Doing my destruction");
    }
    @Override
    public String getSong(){
        Random r = new Random();
        int num = r.nextInt(songs.length);
        return songs[num];
    }
}
