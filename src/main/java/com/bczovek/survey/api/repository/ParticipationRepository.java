package com.bczovek.survey.api.repository;

import com.bczovek.survey.api.model.Participation;

import java.util.List;

public interface ParticipationRepository {

    List<Participation> getParticipationByMember(Integer memberId);
    List<Participation> getParticipationBySurvey(Integer surveyId);

}
