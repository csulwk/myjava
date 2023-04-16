package com.lwk.myredis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyRedisApplicationTests {
    @Autowired
    @Qualifier("jsonRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedisTemplate() {
        redisTemplate.opsForValue().set("rt-aa", "123");
        System.out.println(stringRedisTemplate.opsForValue().get("rt-aa"));
    }

    @Test
    public void testStringRedisTemplate() {
        stringRedisTemplate.opsForValue().set("srt-aa", "123");
        System.out.println(stringRedisTemplate.opsForValue().get("srt-aa"));
        System.out.println(stringRedisTemplate.opsForValue().get("srt-bb"));
    }

}
