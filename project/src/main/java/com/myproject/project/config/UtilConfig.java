package com.myproject.project.config;

import com.myproject.project.util.RandomUUIDGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class UtilConfig {

    @Bean
    public Clock clock(){
        return Clock.systemDefaultZone();
    }

    @Bean
    public RandomUUIDGenerator randomUUIDGenerator(){
        return new RandomUUIDGenerator();
    }
}
