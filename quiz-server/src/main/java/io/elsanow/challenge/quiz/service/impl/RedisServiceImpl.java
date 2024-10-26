package io.elsanow.challenge.quiz.service.impl;

import io.elsanow.challenge.quiz.service.IRedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RedisServiceImpl implements IRedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    public boolean keyExists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getValue(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            return value.toString();
        }
        return null;
    }

    public void addToList(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public List<String> getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public boolean isElementInList(String key, String value) {
        List<String> list = redisTemplate.opsForList().range(key, 0, -1);
        return list != null && list.contains(value);
    }

    public void addZSetScore(String leaderboardKey, String username, double score) {
        redisTemplate.opsForZSet().add(leaderboardKey, username, score);
    }

    public Set<String> getZSet(String leaderboardKey, int start, int end) {
        return redisTemplate.opsForZSet().reverseRange(leaderboardKey, start, end);
    }

    public Double getScore(String leaderboardKey, String username) {
        return redisTemplate.opsForZSet().score(leaderboardKey, username);
    }

    public Long getRank(String leaderboardKey, String username) {
        return redisTemplate.opsForZSet().reverseRank(leaderboardKey, username);
    }
}
