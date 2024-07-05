package com.bczovek.survey.api.model;

public record Participation(Integer memberId,
                            Integer surveyId,
                            ParticipationStatus status,
                            Integer lengthInMinutes) {
}
