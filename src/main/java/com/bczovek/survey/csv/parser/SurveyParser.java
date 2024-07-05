package com.bczovek.survey.csv.parser;

import com.bczovek.survey.api.model.Survey;
import com.bczovek.survey.csv.factory.CsvFileIteratorFactory;
import com.bczovek.survey.csv.model.CsvSurvey;
import com.fasterxml.jackson.databind.MappingIterator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SurveyParser {

    private final CsvFileIteratorFactory iteratorFactory;

    public Map<Integer, Survey> parse() {
        try(MappingIterator<CsvSurvey> surveysIterator =
                    iteratorFactory.createFromFile(createFileUri(), CsvSurvey.class)) {
            Map<Integer, Survey> surveyMap = new HashMap<>();
            while (surveysIterator.hasNext()) {
                Survey survey = convertToDTO(surveysIterator.next());
                surveyMap.put(survey.id(), survey);
            }
            return surveyMap;
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private URI createFileUri() throws URISyntaxException {
        return getClass().getClassLoader().getResource("Surveys.csv").toURI();
    }

    private Survey convertToDTO(CsvSurvey csvSurvey) {
        return new Survey(csvSurvey.id(), csvSurvey.name(), csvSurvey.expectedCompletes(),
                csvSurvey.completionPoints(), csvSurvey.filteredPoints());
    }
}
