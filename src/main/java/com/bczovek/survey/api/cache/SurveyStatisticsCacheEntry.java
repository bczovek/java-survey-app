package com.bczovek.survey.api.cache;

import com.bczovek.survey.api.model.SurveyStatistics;

import java.time.Instant;

public record SurveyStatisticsCacheEntry(SurveyStatistics surveyStatistics, Instant expirationTime) {

    public boolean isExpired() {
        return Instant.now().isAfter(expirationTime);
    }

}
