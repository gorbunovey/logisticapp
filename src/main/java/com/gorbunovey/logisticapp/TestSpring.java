package com.gorbunovey.logisticapp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml"
        );
        MusicPlayer musicPlayer = context.getBean("musicPlayer", MusicPlayer.class);
        musicPlayer.playMusic(MusicPlayer.MusicType.CLASSICAL);
        musicPlayer.playMusic(MusicPlayer.MusicType.ROCK);
        musicPlayer.playMusic(MusicPlayer.MusicType.RAP);
        context.close();
    }
}
