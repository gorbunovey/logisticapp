package com.gorbunovey.logisticapp;

public class ClassicalMusic implements Music {

    public void doMyInit(){
        System.out.println("ClassicalMusic -> Doing my initialization");
    }
    public void doMyDestroy(){
        System.out.println("ClassicalMusic -> Doing my destruction");
    }
    @Override
    public String getSong(){
        return "Hungarian Rhapsody";
    }
}
