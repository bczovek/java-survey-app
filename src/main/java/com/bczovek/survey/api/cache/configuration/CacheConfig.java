package com.bczovek.survey.api.cache.configuration;

import com.bczovek.survey.api.cache.SurveyStatisticsCache;
import com.bczovek.survey.api.cache.impl.InMemorySurveyStatisticsCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Value("${survey-stat.cache.ttl}")
    private long timeToLiveInSeconds;

    @Bean
    public SurveyStatisticsCache surveyStatisticsCache() {
        return new InMemorySurveyStatisticsCache(timeToLiveInSeconds);
    }
}
