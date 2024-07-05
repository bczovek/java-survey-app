package com.bczovek.survey.csv.parser;

import com.bczovek.survey.api.model.Member;
import com.bczovek.survey.api.model.Participation;
import com.bczovek.survey.api.model.ParticipationStatus;
import com.bczovek.survey.api.model.Survey;
import com.bczovek.survey.csv.factory.CsvFileIteratorFactory;
import com.bczovek.survey.csv.model.CsvParticipation;
import com.bczovek.survey.csv.model.RawParticipation;
import com.fasterxml.jackson.databind.MappingIterator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ParticipationParser {

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
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private RawParticipation convertToDTO(CsvParticipation participation) {
        return new RawParticipation(participation.memberId(), participation.surveyId(),
                participation.status(), participation.lengthInMinutes());
    }

    private URI createFileUri() throws URISyntaxException {
        return getClass().getClassLoader().getResource("Participation.csv").toURI();
    }
}
