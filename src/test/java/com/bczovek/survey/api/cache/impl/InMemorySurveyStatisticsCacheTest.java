package com.bczovek.survey.api.cache.impl;

import com.bczovek.survey.api.model.SurveyStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemorySurveyStatisticsCacheTest {

    private final InMemorySurveyStatisticsCache underTest = new InMemorySurveyStatisticsCache(1L);

    @Test
    void testCleanupCacheShouldDeleteExpiredEntries() throws InterruptedException {
        underTest.cache(1, new SurveyStatistics(1, "Name", 1L, 1L,1L, 1.0));

        Thread.sleep(1000);

        underTest.cleanupCache();

        assertFalse(underTest.retrieve(1).isPresent());
    }

    @Test
    void testRetrieveShouldNotReturnExpiredEntry() throws InterruptedException {
        underTest.cache(1, new SurveyStatistics(1, "Name", 1L, 1L,1L, 1.0));

        Thread.sleep(1000);

        assertFalse(underTest.retrieve(1).isPresent());
    }
}