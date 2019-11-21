package com.gorbunovey.logisticapp;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Random;

@Component
public class RapMusic implements Music {

    String[] songs = {"50 cent - Candy shop", "Eminem - Loose yourself", "Some gangsta rap..."};

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
    public String getSong(){
        Random r = new Random();
        int num = r.nextInt(songs.length);
        return songs[num];
    }
}
