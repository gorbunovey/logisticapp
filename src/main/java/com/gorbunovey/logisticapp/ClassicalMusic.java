package com.gorbunovey.logisticapp;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Random;


public class ClassicalMusic implements Music {

    String[] songs = {"Hungarian Rhapsody", "Requiem", "Symphony No. 6"};

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
        Random r = new Random();
        int num = r.nextInt(songs.length);
        return songs[num];
    }
}
