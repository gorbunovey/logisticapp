package com.gorbunovey.logisticapp;

import org.springframework.beans.factory.annotation.Value;


import java.util.ArrayList;
import java.util.List;


public class MusicPlayer {

    private List<Music> playlist;

    @Value("${musicPlayer.name}")
    private String name;
    @Value("${musicPlayer.volume}")
    private int volume;

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
