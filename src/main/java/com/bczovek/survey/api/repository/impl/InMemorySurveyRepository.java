package com.bczovek.survey.api.repository.impl;

import com.bczovek.survey.api.model.Survey;
import com.bczovek.survey.api.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class InMemorySurveyRepository implements SurveyRepository {

    private final Map<Integer, Survey> surveys;

    @Override
    public Survey getSurveyById(Integer id) {
        return surveys.get(id);
    }

    @Override
    public List<Survey> getSurveysById(List<Integer> id) {
        return List.of();
    }
}
