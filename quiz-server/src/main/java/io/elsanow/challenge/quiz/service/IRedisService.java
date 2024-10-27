package io.elsanow.challenge.quiz.service;

import java.util.List;
import java.util.Set;

public interface IRedisService {

    void deleteValue(String key);

    boolean keyExists(String key);

    Long getTTL(String key);

    void setValue(String key, String value);

    void setValueWithExpire(String key, String value, int seconds);

    String getValue(String key);

    void addToList(String key, String value);

    List<String> getList(String key);

    boolean isElementInList(String key, String value);

    void addZSetScore(String leaderboardKey, String username, double score);

    Set<String> getZSet(String leaderboardKey, int start, int end);

    Double getScore(String leaderboardKey, String username);

    Long getRank(String leaderboardKey, String username);
}
