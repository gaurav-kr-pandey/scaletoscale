package com.s2s.scaletoscale.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutionException;
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
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build(cacheLoader);
    }

    public int generateOtp(){
        int lowerBound = 10000;
        int upperBound = 999999;
        Random random = new Random();
        return random.nextInt(upperBound-lowerBound) + lowerBound;
    }

    public void storeOtp(String key, String value){
        cache.put(key,value);
    }

    public boolean isCorrectOtp(String key, String otp) throws ExecutionException {
        return cache.get(key).equals(otp);
    }

}
