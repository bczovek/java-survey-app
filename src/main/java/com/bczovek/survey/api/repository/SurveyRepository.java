package com.bczovek.survey.api.repository;

import com.bczovek.survey.api.model.Survey;

import java.util.List;

public interface SurveyRepository {

    Survey getSurveyById(Integer id);
    List<Survey> getSurveysById(List<Integer> ids);

}
