package org.com.util;


import redis.clients.jedis.Jedis;

public class RedisCacheService {

    private final Jedis jedis;

    public RedisCacheService() {
        this.jedis = new Jedis("localhost", 6379);
    }

    public void put(String key, String value, int expireSeconds) {
        jedis.setex(key, expireSeconds, value);
    }

    public String get(String key) {
        return jedis.get(key);
    }
}
