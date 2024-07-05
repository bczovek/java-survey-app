package com.bczovek.survey.api.repository.impl;

import com.bczovek.survey.api.model.Member;
import com.bczovek.survey.api.model.Participation;
import com.bczovek.survey.api.model.Survey;
import com.bczovek.survey.api.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class InMemoryParticipationRepository implements ParticipationRepository {

    private final List<Participation> participationList;

    @Override
    public List<Participation> getParticipationByMember(Member member) {
        return participationList.stream()
                .filter(participation -> participation.memberId().equals(member.id()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Participation> getParticipationBySurvey(Survey survey) {
        return participationList.stream()
                .filter(participation -> participation.surveyId().equals(survey.id()))
                .collect(Collectors.toList());
    }
}
