package org.redis;

import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.stream.Collectors;

public class RedisMap implements Map<String, String> {

    private final Jedis jedis;

    public RedisMap(Jedis jedis){
        this.jedis = jedis;
    }


    public void close() {
        if (jedis != null) {
            jedis.close();
        }
    }

    @Override
    public int size() {
        return (int)jedis.dbSize();
    }

    @Override
    public boolean isEmpty() {
        return jedis.dbSize() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return jedis.exists(key.toString());
    }

    @Override
    public boolean containsValue(Object value) {
        return jedis.keys("*").stream()
                .anyMatch(s -> Objects.equals(jedis.get(s), value));
    }

    @Override
    public String get(Object key) {
        return jedis.get((String) key);
    }

    @Override
    public String put(String key, String value) {
        return jedis.set(key, value);
    }

    @Override
    public String remove(Object key) {
        String oldValue = jedis.get((String) key);
        jedis.del((String) key);
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        m.forEach(jedis::set);
    }

    @Override
    public void clear() {
        jedis.flushDB();
    }

    @Override
    public Set<String> keySet() {
        return jedis.keys("*");
    }

    @Override
    public Collection<String> values() {
        return jedis.keys("*").stream()
                .map(jedis::get)
                .collect(Collectors.toList());
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return jedis.keys("*").stream()
                .map(k -> Map.entry(k, jedis.get(k)))
                .collect(Collectors.toSet());
    }
}
