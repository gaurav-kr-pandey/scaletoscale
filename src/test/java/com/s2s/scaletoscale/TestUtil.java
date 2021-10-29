package com.s2s.scaletoscale;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import static com.s2s.scaletoscale.utils.BlogUtils.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.Date;
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
                    .expireAfterAccess(2, TimeUnit.MINUTES)
                    .build(cacheLoader);
            String key = "gaurav17p@gmail.com";
        int lowerBound = 10000;
        int upperBound = 999999;
        Random random = new Random();
        int value = random.nextInt(upperBound-lowerBound) + lowerBound;
        System.out.println(value);
        cache.put(key,value+"");
        System.out.println(cache.get(key));
        //Thread.sleep(2000);
        //cache.refresh(key);
        Assert.assertEquals(cache.get(key),value+"");

    }

    @Test
    public void test(){System.out.println(
        new Date(System.currentTimeMillis()));
        String str = "scaletoscale@gmail.com";
        String morphOnce = morph(str);
        String morphTwice = morph(morphOnce);
        System.out.println(str);
        System.out.println(morphOnce);
        System.out.println(morphTwice);
        Assert.assertEquals(str,getTextified(getTextified(morphTwice)));
        System.out.println(new Date(System.currentTimeMillis()));
    }

    private String morph(String plain){
        String b64encoded = Base64.getEncoder().encodeToString(plain.getBytes());
        String reverse = new StringBuffer(b64encoded).reverse().toString();
        StringBuilder tmp = new StringBuilder();
        final int OFFSET = 4;
        for (int i = 0; i < reverse.length(); i++) {
            tmp.append((char)(reverse.charAt(i) + OFFSET));
        }
        return tmp.toString();}
}
