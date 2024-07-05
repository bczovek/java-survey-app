package com.bczovek.survey.api.cache.impl;

import com.bczovek.survey.api.cache.SurveyStatisticsCache;
import com.bczovek.survey.api.cache.SurveyStatisticsCacheEntry;
import com.bczovek.survey.api.model.SurveyStatistics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemorySurveyStatisticsCache implements SurveyStatisticsCache {

    private final Map<Integer, SurveyStatisticsCacheEntry> cache = new HashMap<>();
    @Value("${survey-stat.cache.ttl}")
    private Long timeToLiveInMinutes;

    @Override
    public void cache(Integer surveyId, SurveyStatistics surveyStatistics) {
        cache.put(surveyId, new SurveyStatisticsCacheEntry(surveyStatistics, calculateExpirationTime()));
    }

    private Instant calculateExpirationTime() {
        return Instant.now().plusSeconds(timeToLiveInMinutes * 60);
    }

    @Override
    public Optional<SurveyStatistics> retrieve(Integer surveyId) {
        SurveyStatisticsCacheEntry surveyStatisticsCacheEntry = cache.get(surveyId);
        if (surveyStatisticsCacheEntry == null || surveyStatisticsCacheEntry.isExpired()) {
            return Optional.empty();
        }
        return Optional.of(surveyStatisticsCacheEntry.surveyStatistics());
    }

    @Scheduled(cron = "0 */10 * ? * *")
    private void cleanupCache() {
        cache.entrySet().stream()
                .filter(surveyStatisticsCacheEntry -> surveyStatisticsCacheEntry.getValue().isExpired())
                .forEach(surveyStatisticsCacheEntry -> cache.remove(surveyStatisticsCacheEntry.getKey()));
    }
}
