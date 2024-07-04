package com.bczovek.survey.api.repository.impl;

import com.bczovek.survey.api.model.MemberDTO;
import com.bczovek.survey.api.model.ParticipationDTO;
import com.bczovek.survey.api.model.SurveyDTO;
import com.bczovek.survey.api.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class InMemoryParticipationRepository implements ParticipationRepository {

    private final List<ParticipationDTO> participationList;

    @Override
    public List<ParticipationDTO> getParticipationByMember(MemberDTO member) {
        return participationList;
    }

    @Override
    public List<ParticipationDTO> getParticipationBySurvey(SurveyDTO survey) {
        return List.of();
    }
}
