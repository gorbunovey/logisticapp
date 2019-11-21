package com.gorbunovey.logisticapp;

import org.springframework.beans.factory.annotation.Value;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;


public class MusicPlayer {

    private List<Music> playlist;

    @Value("${musicPlayer.name}")
    private String name;
    @Value("${musicPlayer.volume}")
    private int volume;

    @PostConstruct
    public void doMyInit(){
        System.out.println("MusicPlayer -> Doing my initialization");
    }
    @PreDestroy
    public void doMyDestroy(){
        System.out.println("MusicPlayer -> Doing my destruction");
    }

    public MusicPlayer(List<Music> playlist) {
        this.playlist = playlist;
    }
    public MusicPlayer() {
        this.playlist = new ArrayList<Music>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void playMusic(){
        if(playlist == null || playlist.isEmpty()) {
            System.out.println("playlist is empty");
            return;
        }
        System.out.println("Playing: ");
        for (Music song: playlist) {
            System.out.println(song.getSong());
        }
    }
}
