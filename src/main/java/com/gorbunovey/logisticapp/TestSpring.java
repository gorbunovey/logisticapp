package com.gorbunovey.logisticapp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestSpring {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                SpringConfig.class
        );
        MusicPlayer musicPlayer = context.getBean("musicPlayer", MusicPlayer.class);
        System.out.println("Player name: " + musicPlayer.getName());
        System.out.println("Player volume: " + musicPlayer.getVolume());
        musicPlayer.playMusic();
        context.close();
    }
}
