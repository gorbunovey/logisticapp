package com.gorbunovey.logisticapp;

import org.springframework.context.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:musicPlayer.properties")
public class SpringConfig {

    @Bean
    public ClassicalMusic classicalMusic(){
        return new ClassicalMusic();
    }
    @Bean
    public RockMusic rockMusic(){
        return new RockMusic();
    }
    @Bean
    public RapMusic rapMusic(){
        return new RapMusic();
    }
    @Bean
    @Scope("prototype")
    public MusicPlayer musicPlayer(){
        List<Music> playlist = new ArrayList<Music>();
        playlist.add(classicalMusic());
        playlist.add(rockMusic());
        playlist.add(rapMusic());
        return new MusicPlayer(playlist);
    }
}
