package com.bczovek.survey.api.cache.configuration;

import com.bczovek.survey.api.cache.SurveyStatisticsCache;
import com.bczovek.survey.api.cache.impl.InMemorySurveyStatisticsCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public SurveyStatisticsCache surveyStatisticsCache() {
        return new InMemorySurveyStatisticsCache(1440L);
    }

}
