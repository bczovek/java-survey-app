package com.bczovek.survey.api.model;

public record SurveyStatistics(Integer surveyId, String surveyName, Long completedQuestionnaires,
                               Long filteredMembers, Long rejectedMembers, Double averageTime) {
}
