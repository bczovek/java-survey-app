package com.bczovek.survey.api.repository.impl;

import com.bczovek.survey.api.model.Survey;
import com.bczovek.survey.api.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class InMemorySurveyRepository implements SurveyRepository {

    private final Map<Integer, Survey> surveys;

    @Override
    public Survey getSurveyById(Integer id) {
        return surveys.get(id);
    }

    @Override
    public List<Survey> getSurveysByIds(List<Integer> ids) {
        return surveys.entrySet()
                .stream()
                .filter(surveyEntry -> ids.contains(surveyEntry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
