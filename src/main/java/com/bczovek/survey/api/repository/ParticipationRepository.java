package com.bczovek.survey.api.repository;

import com.bczovek.survey.api.model.MemberDTO;
import com.bczovek.survey.api.model.ParticipationDTO;
import com.bczovek.survey.api.model.SurveyDTO;

import java.util.List;

public interface ParticipationRepository {

    List<ParticipationDTO> getParticipationByMember(MemberDTO member);
    List<ParticipationDTO> getParticipationBySurvey(SurveyDTO survey);

}
