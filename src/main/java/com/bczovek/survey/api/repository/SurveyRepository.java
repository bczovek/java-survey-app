package com.bczovek.survey.api.repository;

import com.bczovek.survey.api.model.SurveyDTO;

import java.util.List;

public interface SurveyRepository {

    SurveyDTO getSurveyById(Integer id);
    List<SurveyDTO> getSurveysById(List<Integer> ids);

}
