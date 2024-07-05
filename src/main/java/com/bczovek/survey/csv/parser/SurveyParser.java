package com.bczovek.survey.csv.parser;

import com.bczovek.survey.api.model.Survey;
import com.bczovek.survey.csv.exception.InvalidCsvFileContent;
import com.bczovek.survey.csv.factory.CsvFileIteratorFactory;
import com.bczovek.survey.csv.model.CsvSurvey;
import com.bczovek.survey.csv.factory.CsvFileFactory;
import com.fasterxml.jackson.databind.MappingIterator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SurveyParser {

    @Value("${surveys.file}")
    private String surveysCsvFile;
    private final CsvFileIteratorFactory iteratorFactory;
    private final CsvFileFactory csvFileFactory;

    public Map<Integer, Survey> parse() {
        try(MappingIterator<CsvSurvey> surveysIterator =
                    iteratorFactory.createFromFile(csvFileFactory.createFileFromResource(surveysCsvFile),
                            CsvSurvey.class)) {
            Map<Integer, Survey> surveyMap = new HashMap<>();
            while (surveysIterator.hasNext()) {
                Survey survey = convertToDTO(surveysIterator.next());
                surveyMap.put(survey.id(), survey);
            }
            return surveyMap;
        } catch (IOException e) {
            throw new InvalidCsvFileContent(STR."Error occured while processing \{surveysCsvFile}", e);
        }
    }

    private Survey convertToDTO(CsvSurvey csvSurvey) {
        return new Survey(csvSurvey.id(), csvSurvey.name(), csvSurvey.expectedCompletes(),
                csvSurvey.completionPoints(), csvSurvey.filteredPoints());
    }
}
