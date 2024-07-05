package com.bczovek.survey.api.model;

public record Participation(Member member,
                            Survey survey,
                            ParticipationStatus status,
                            Integer lengthInMinutes) {
}
