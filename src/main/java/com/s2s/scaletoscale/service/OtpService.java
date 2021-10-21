package com.s2s.scaletoscale.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {

    private LoadingCache<String,String> cache;
    @PostConstruct
    void init(){
        CacheLoader<String,String> cacheLoader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                return key.trim().toUpperCase(Locale.ROOT);
            }
        };
        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(6, TimeUnit.MINUTES)
                .build(cacheLoader);
    }




}
