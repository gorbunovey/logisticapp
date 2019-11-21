package com.gorbunovey.logisticapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MusicPlayer {
    public enum MusicType {CLASSICAL, ROCK, RAP}
    @Autowired
    @Qualifier("classicalMusic")
    private Music musicClassical;
    @Autowired
    @Qualifier("rockMusic")
    private Music musicRock;
    @Autowired
    @Qualifier("rapMusic")
    private Music musicRap;
    private String name = "";
    private int volume = 100;

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

    public void playMusic(MusicType type){
        System.out.println("Playing: ");
        switch (type) {
            case CLASSICAL:
                System.out.println(this.musicClassical.getSong());
                break;
            case ROCK:
                System.out.println(this.musicRock.getSong());
                break;
            case RAP:
                System.out.println(this.musicRap.getSong());
                break;
        }
    }
}
