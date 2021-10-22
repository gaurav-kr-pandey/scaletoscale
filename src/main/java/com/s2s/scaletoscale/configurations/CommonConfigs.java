package com.s2s.scaletoscale.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfigs {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
