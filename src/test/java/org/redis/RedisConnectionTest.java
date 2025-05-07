package org.redis;

import redis.clients.jedis.Jedis;

public class RedisConnectionTest {
    public static void main(String[] args) {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            String response = jedis.ping();
            System.out.println("Redis responded to PING: " + response);
        } catch (Exception e) {
            System.err.println("Ошибка подключения к Redis:");
            e.printStackTrace();
        }
    }
}
