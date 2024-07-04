package com.bczovek.survey.api.model;

public record ParticipationDTO(MemberDTO member,
                               SurveyDTO survey,
                               ParticipationStatus status,
                               Integer lengthInMinutes) {
}
