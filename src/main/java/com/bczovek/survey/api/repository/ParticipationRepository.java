package com.bczovek.survey.api.repository;

import com.bczovek.survey.api.model.Member;
import com.bczovek.survey.api.model.Participation;
import com.bczovek.survey.api.model.Survey;

import java.util.List;

public interface ParticipationRepository {

    List<Participation> getParticipationByMember(Member member);
    List<Participation> getParticipationBySurvey(Survey survey);

}
