package com.bczovek.survey.api.model;

public record SurveyStatistics(Integer surveyId, String surveyName, Integer completedQuestionnaires,
                               Integer filteredMembers, Integer rejectedMembers, Integer averageTime) {
}
