package org.redis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RedisMapTest {

    private RedisMap redisMap;

    private Jedis jedis;

    @BeforeEach
    public void setup() {
        jedis = new Jedis("localhost", 6379);
        jedis.flushDB();
        redisMap = new RedisMap(jedis);
    }

    @AfterEach
    public void teardown() {
        jedis.close();
    }

    @Test
    public void testPutAndGet() {
        redisMap.put("key1", "value1");
        assertEquals("value1", redisMap.get("key1"));
    }

    @Test
    public void testSizeAndClear() {
        redisMap.put("k", "1");
        redisMap.put("v", "2");
        assertEquals(2, redisMap.size());

        redisMap.clear();
        assertTrue(redisMap.isEmpty());
    }

    @Test
    public void testRemove() {
        redisMap.put("test", "value");
        redisMap.remove("test");
        assertFalse(redisMap.containsKey("test"));
    }

    @Test
    public void testContainsKey() {
        redisMap.put("test", "value");
        assertTrue(redisMap.containsKey("test"));
    }

    @Test
    public void testContainsValue() {
        redisMap.put("test", "value");
        assertTrue(redisMap.containsValue("value"));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(redisMap.isEmpty());
    }

    @Test
    public void testPutAll() {
        redisMap.put("key1", "value1");
        redisMap.put("key2", "value2");
        redisMap.put("key3", "value3");
        assertTrue(redisMap.containsKey("key1"));
        assertTrue(redisMap.containsKey("key2"));
        assertEquals(3, redisMap.size());
    }

    @Test
    public void testEntrySet() {
        redisMap.put("key1", "value1");
        redisMap.put("key2", "value2");

        Set<Map.Entry<String, String>> entries = redisMap.entrySet();

        assertEquals(2, entries.size());

        Map<String, String> expected = Map.of("key1", "value1", "key2", "value2");
        for (Map.Entry<String, String> entry : entries) {
            assertEquals(expected.get(entry.getKey()), entry.getValue());
        }
    }

    @Test
    public void testKeySet() {
        redisMap.put("key1", "value1");
        redisMap.put("key2", "value2");
        Set<String> keys = redisMap.keySet();
        assertEquals(2, keys.size());
        assertTrue(keys.contains("key1"));
        assertTrue(keys.contains("key2"));
    }

    @Test
    public void testValues() {
        redisMap.put("key1", "value1");
        redisMap.put("key2", "value2");

        Collection<String> values = redisMap.values();
        assertEquals(2, values.size());
        assertTrue(values.contains("value1"));
        assertTrue(values.contains("value2"));
    }
  
}