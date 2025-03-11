package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPooled;
import java.time.Instant;
import java.util.Set;

public class UpstashPriorityQueue {
    private final JedisPooled jedis;
    private static final String QUEUE_KEY = "priority_queue";
    public UpstashPriorityQueue(String redisUrl, String redisToken) {
        this.jedis = new JedisPooled(redisUrl, 6379, redisToken);
    }
    public void add(int priority, String data) {
        double score = priority + (Instant.now().toEpochMilli() / 1_000_000_000.0);
        jedis.zadd(QUEUE_KEY, score, data);
    }
    public String poll() {
        Set<String> items = jedis.zrevrange(QUEUE_KEY, 0, 0); // Get highest priority
        if (items.isEmpty()) return null;
        String item = items.iterator().next();
        jedis.zrem(QUEUE_KEY, item);
        return item;
    }
    public String peek() {
        Set<String> items = jedis.zrevrange(QUEUE_KEY, 0, 0);
        return items.isEmpty() ? null : items.iterator().next();
    }
    public long size() {
        return jedis.zcard(QUEUE_KEY);
    }
    public boolean isEmpty() {
        return size() == 0;
    }
}
