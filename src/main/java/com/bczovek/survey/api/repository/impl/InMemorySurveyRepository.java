package com.bczovek.survey.api.repository.impl;

import com.bczovek.survey.api.model.Survey;
import com.bczovek.survey.api.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InMemorySurveyRepository implements SurveyRepository {

    private final Map<Integer, Survey> surveys;

    @Override
    public List<Survey> getAllSurveys() {
        return new ArrayList<>(surveys.values());
    }

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
                .toList();
    }
}
