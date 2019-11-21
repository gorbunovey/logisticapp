package com.gorbunovey.logisticapp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.gorbunovey.logisticapp")
@PropertySource("classpath:musicPlayer.properties")
public class SpringConfig {
}
