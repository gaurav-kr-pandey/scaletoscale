package com.s2s.scaletoscale.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class CommonConfigs {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
