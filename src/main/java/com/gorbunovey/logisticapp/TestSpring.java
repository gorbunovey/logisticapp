package com.gorbunovey.logisticapp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml"
        );
        //Music music = context.getBean("musicBean", Music.class);
        //MusicPlayer musicPlayer = new MusicPlayer(music);
        MusicPlayer musicPlayer1 = context.getBean("musicPlayer", MusicPlayer.class);
        MusicPlayer musicPlayer2 = context.getBean("musicPlayer", MusicPlayer.class);
        musicPlayer1.playMusic();
        musicPlayer2.playMusic();
        System.out.println(musicPlayer1.getName());
        System.out.println(musicPlayer1.getVolume());
        context.close();
    }
}
