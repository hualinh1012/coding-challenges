package io.elsanow.challenge.quiz.service;

import io.elsanow.challenge.quiz.service.impl.RedisServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedisServiceImplTest {

    @InjectMocks
    private RedisServiceImpl redisService;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @Mock
    private ListOperations<String, String> listOperations;

    @Mock
    private ZSetOperations<String, String> zSetOperations;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
    }

    @Test
    void testDeleteValue() {
        redisService.deleteValue("testKey");
        verify(redisTemplate).delete("testKey");
    }

    @Test
    void testKeyExists_KeyExists() {
        when(redisTemplate.hasKey("testKey")).thenReturn(true);
        assertTrue(redisService.keyExists("testKey"));
    }

    @Test
    void testKeyExists_KeyDoesNotExist() {
        when(redisTemplate.hasKey("testKey")).thenReturn(false);
        assertFalse(redisService.keyExists("testKey"));
    }

    @Test
    void testGetTTL() {
        when(redisTemplate.getExpire("testKey", TimeUnit.SECONDS)).thenReturn(60L);
        assertEquals(60L, redisService.getTTL("testKey"));
    }

    @Test
    void testSetValue() {
        redisService.setValue("testKey", "testValue");
        verify(redisTemplate).expire("testKey", 300, TimeUnit.SECONDS);
    }

    @Test
    void testGetValue_ValueExists() {
        when(redisTemplate.opsForValue().get("testKey")).thenReturn("testValue");
        String value = redisService.getValue("testKey");
        assertEquals("testValue", value);
    }

    @Test
    void testGetValue_ValueDoesNotExist() {
        when(redisTemplate.opsForValue().get("testKey")).thenReturn(null);
        String value = redisService.getValue("testKey");
        assertNull(value);
    }

    @Test
    void testAddToList() {
        redisService.addToList("testList", "testValue");
        verify(redisTemplate).expire("testList", 300, TimeUnit.SECONDS);
    }

    @Test
    void testGetList() {
        when(redisTemplate.opsForList().range("testList", 0, -1)).thenReturn(Arrays.asList("value1", "value2"));
        List<String> list = redisService.getList("testList");
        assertEquals(2, list.size());
        assertEquals("value1", list.get(0));
        assertEquals("value2", list.get(1));
    }

    @Test
    void testIsElementInList_Exists() {
        when(redisTemplate.opsForList().range("testList", 0, -1)).thenReturn(Arrays.asList("value1", "value2"));
        assertTrue(redisService.isElementInList("testList", "value1"));
    }

    @Test
    void testIsElementInList_NotExists() {
        when(redisTemplate.opsForList().range("testList", 0, -1)).thenReturn(Arrays.asList("value1", "value2"));
        assertFalse(redisService.isElementInList("testList", "value3"));
    }

    @Test
    void testAddZSetScore() {
        redisService.addZSetScore("leaderboard", "user1", 100.0);
        verify(redisTemplate).expire("leaderboard", 300, TimeUnit.SECONDS);
    }

    @Test
    void testIncreaseZSetScore() {
        redisService.increaseZSetScore("leaderboard", "user1", 50.0);
        verify(redisTemplate).expire("leaderboard", 300, TimeUnit.SECONDS);
    }

    @Test
    void testGetZSet() {
        when(redisTemplate.opsForZSet().reverseRange("leaderboard", 0, 10)).thenReturn(Set.of("user1", "user2"));
        Set<String> users = redisService.getZSet("leaderboard", 0, 10);
        assertEquals(2, users.size());
        assertTrue(users.contains("user1"));
        assertTrue(users.contains("user2"));
    }

    @Test
    void testGetScore() {
        when(redisTemplate.opsForZSet().score("leaderboard", "user1")).thenReturn(150.0);
        Double score = redisService.getScore("leaderboard", "user1");
        assertEquals(150.0, score);
    }

    @Test
    void testSetHashValue() {
        redisService.setHashValue("hashName", "key1", 123);
        verify(hashOperations).put("hashName", "key1", 123);
    }

    @Test
    void testGetHashValue() {
        when(hashOperations.get("hashName", "key1")).thenReturn(123);
        Integer value = redisService.getHashValue("hashName", "key1");
        assertEquals(123, value);
    }
}