package com.gorbunovey.logisticapp;

public class RockMusic implements Music {
    public void doMyInit(){
        System.out.println("RockMusic -> Doing my initialization");
    }
    public void doMyDestroy(){
        System.out.println("RockMusic -> Doing my destruction");
    }
    @Override
    public String getSong() {
        return "Highway to hell";
    }
}
