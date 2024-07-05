package com.bczovek.survey.api.cache;

import com.bczovek.survey.api.model.SurveyStatistics;

import java.util.Optional;

public interface SurveyStatisticsCache {

    void cache(Integer surveyId, SurveyStatistics surveyStatistics);

    Optional<SurveyStatistics> retrieve(Integer surveyId);
}
