package com.bczovek.survey.api.cache.impl;

import com.bczovek.survey.api.cache.SurveyStatisticsCache;
import com.bczovek.survey.api.cache.SurveyStatisticsCacheEntry;
import com.bczovek.survey.api.model.SurveyStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
public class InMemorySurveyStatisticsCache implements SurveyStatisticsCache {

    private final Map<Integer, SurveyStatisticsCacheEntry> cache = new HashMap<>();
    private final long timeToLiveInSeconds;

    @Override
    public void cache(Integer surveyId, SurveyStatistics surveyStatistics) {
        cache.put(surveyId, new SurveyStatisticsCacheEntry(surveyStatistics, calculateExpirationTime()));
    }

    private Instant calculateExpirationTime() {
        return Instant.now().plusSeconds(timeToLiveInSeconds);
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
    public void cleanupCache() {
        cache.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
}
