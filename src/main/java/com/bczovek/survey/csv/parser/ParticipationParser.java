package com.bczovek.survey.csv.parser;

import com.bczovek.survey.csv.exception.InvalidCsvFileContent;
import com.bczovek.survey.csv.exception.InvalidCsvFileReference;
import com.bczovek.survey.csv.factory.CsvFileIteratorFactory;
import com.bczovek.survey.csv.model.CsvParticipation;
import com.bczovek.survey.csv.model.RawParticipation;
import com.fasterxml.jackson.databind.MappingIterator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ParticipationParser {

    @Value("${participation.file}")
    private String participationCsvFile;
    private final CsvFileIteratorFactory iteratorFactory;

    public List<RawParticipation> parse() {
        try (MappingIterator<CsvParticipation> iterator =
                     iteratorFactory.createFromFile(createFileUri(), CsvParticipation.class)) {
            List<RawParticipation> participationList = new ArrayList<>();
            while (iterator.hasNext()) {
                CsvParticipation csvParticipation = iterator.next();
                RawParticipation participation = convertToDTO(csvParticipation);
                participationList.add(participation);
            }
            return participationList;
        } catch (URISyntaxException e) {
            throw new InvalidCsvFileReference(STR."Could not find \{participationCsvFile} CSV file in the resources", e);
        } catch (IOException e) {
            throw new InvalidCsvFileContent(STR."Error occured while processing \{participationCsvFile}", e);
        }
    }

    private RawParticipation convertToDTO(CsvParticipation participation) {
        return new RawParticipation(participation.memberId(), participation.surveyId(),
                participation.status(), participation.lengthInMinutes());
    }

    private URI createFileUri() throws URISyntaxException {
        return getClass().getClassLoader().getResource(participationCsvFile).toURI();
    }
}
