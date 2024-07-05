package com.bczovek.survey.api.repository.impl;

import com.bczovek.survey.api.model.Participation;
import com.bczovek.survey.api.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InMemoryParticipationRepository implements ParticipationRepository {

    private final List<Participation> participationList;

    @Override
    public List<Participation> getParticipationByMember(Integer memberId) {
        return participationList.stream()
                .filter(participation -> participation.memberId().equals(memberId))
                .toList();
    }

    @Override
    public List<Participation> getParticipationBySurvey(Integer surveyId) {
        return participationList.stream()
                .filter(participation -> participation.surveyId().equals(surveyId))
                .toList();
    }
}
