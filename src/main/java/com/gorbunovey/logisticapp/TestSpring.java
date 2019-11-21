package com.gorbunovey.logisticapp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                SpringConfig.class
        );
        MusicPlayer musicPlayer = context.getBean("musicPlayer", MusicPlayer.class);
        System.out.println("Player name: " + musicPlayer.getName());
        System.out.println("Player name: " + musicPlayer.getVolume());
        musicPlayer.playMusic(MusicPlayer.MusicType.CLASSICAL);
        musicPlayer.playMusic(MusicPlayer.MusicType.ROCK);
        musicPlayer.playMusic(MusicPlayer.MusicType.RAP);
        context.close();
    }
}
