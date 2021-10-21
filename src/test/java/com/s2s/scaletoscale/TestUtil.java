package com.s2s.scaletoscale;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TestUtil {

    @Test
    public void testGuavaCacheLibrary() throws ExecutionException, InterruptedException {
        LoadingCache<String,String> cache;
            CacheLoader<String,String> cacheLoader = new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    return key.trim().toUpperCase(Locale.ROOT);
                }
            };
            cache = CacheBuilder.newBuilder()
                    .expireAfterAccess(2, TimeUnit.SECONDS)
                    .build(cacheLoader);
            String key = "gaurav17p@gmail.com";
        int lowerBound = 10000;
        int upperBound = 999999;
        Random random = new Random();
        int value = random.nextInt(upperBound-lowerBound) + lowerBound;
        cache.put(key,value+"");
        System.out.println(cache.get(key));
        //Thread.sleep(2000);
        //cache.refresh(key);
        Assert.assertEquals(cache.get(key),value+"");

    }
}
