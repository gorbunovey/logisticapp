package com.gorbunovey.logisticapp;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Random;

public class RapMusic implements Music {

    String[] songs = {"50 cent - Candy shop", "Eminem - Loose yourself", "Some gangsta rap..."};

    @PostConstruct
    public void doMyInit(){
        System.out.println("RapMusic -> Doing my initialization");
    }
    @PreDestroy
    public void doMyDestroy(){
        System.out.println("RapMusic -> Doing my destruction");
    }
    @Override
    public String getSong(){
        Random r = new Random();
        int num = r.nextInt(songs.length);
        return songs[num];
    }
}
