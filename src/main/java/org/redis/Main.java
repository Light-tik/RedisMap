package org.redis;

import redis.clients.jedis.Jedis;

public class Main {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        RedisMap redisMap = new RedisMap(jedis);

        redisMap.clear();

        redisMap.put("key", "value");
        redisMap.put("k", "v");

        System.out.println("Get key: " + redisMap.get("key"));
        System.out.println("Contains key 'k': " + redisMap.containsKey("k"));
        System.out.println("Size: " + redisMap.size());

        redisMap.remove("k");
        System.out.println("After remove 'k', size: " + redisMap.size());
        redisMap.close();
    }
}