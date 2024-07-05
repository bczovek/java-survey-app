package com.bczovek.survey.csv.parser;

import com.bczovek.survey.csv.exception.InvalidCsvFileContent;
import com.bczovek.survey.csv.factory.CsvFileIteratorFactory;
import com.bczovek.survey.csv.model.CsvParticipation;
import com.bczovek.survey.csv.util.URIUtil;
import com.fasterxml.jackson.databind.MappingIterator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ParticipationParser {

    @Value("${participation.file}")
    private String participationCsvFile;
    private final CsvFileIteratorFactory iteratorFactory;

    public List<CsvParticipation> parse() {
        try (MappingIterator<CsvParticipation> iterator =
                     iteratorFactory.createFromFile(URIUtil.createFileUriFromResource(participationCsvFile),
                             CsvParticipation.class)) {
            List<CsvParticipation> participationList = new ArrayList<>();
            while (iterator.hasNext()) {
                participationList.add(iterator.next());
            }
            return participationList;
        } catch (IOException e) {
            throw new InvalidCsvFileContent(STR."Error occured while processing \{participationCsvFile}", e);
        }
    }
}
