package com.gorbunovey.logisticapp;

public class RapMusic implements Music {
    public static RapMusic getRapMusic(){
        return new RapMusic();
    }
    private RapMusic(){}
    public void doMyInit(){
        System.out.println("RapMusic -> Doing my initialization");
    }
    public void doMyDestroy(){
        System.out.println("RapMusic -> Doing my destruction");
    }
    @Override
    public String getSong() {
        return "50 cent - Candy shop";
    }
}
