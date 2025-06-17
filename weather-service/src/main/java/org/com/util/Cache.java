package org.com.util;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache<T> {

    private record CacheItem<T>(T value, Instant expiresAt) {}

    private final long ttlSeconds;
    private final Map<String, CacheItem<T>> store = new ConcurrentHashMap<>();

    public Cache(long ttlSeconds) {
        this.ttlSeconds = ttlSeconds;
    }

    public T get(String key) {
        CacheItem<T> item = store.get(key);
        if (item == null || Instant.now().isAfter(item.expiresAt)) {
            store.remove(key);
            return null;
        }
        return item.value;
    }

    public void put(String key, T value) {
        store.put(key, new CacheItem<>(value, Instant.now().plusSeconds(ttlSeconds)));
    }
}
