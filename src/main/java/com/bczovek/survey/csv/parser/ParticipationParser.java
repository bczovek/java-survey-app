package com.bczovek.survey.csv.parser;

import com.bczovek.survey.api.model.Member;
import com.bczovek.survey.api.model.Participation;
import com.bczovek.survey.api.model.ParticipationStatus;
import com.bczovek.survey.api.model.Survey;
import com.bczovek.survey.csv.factory.CsvFileIteratorFactory;
import com.bczovek.survey.csv.model.CsvParticipation;
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

    public List<Participation> parse() {
        try (MappingIterator<CsvParticipation> iterator =
                     iteratorFactory.createFromFile(createFileUri(), CsvParticipation.class)) {
            List<Participation> participationList = new ArrayList<>();
            while (iterator.hasNext()) {
                CsvParticipation csvParticipation = iterator.next();
                Participation participation = convertToDTO(csvParticipation);
                participationList.add(participation);
            }
            return participationList;
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private Participation convertToDTO(CsvParticipation participation) {
        ParticipationStatus status = ParticipationStatus.values()[participation.status() - 1];
        return new Participation(participation.memberId(), participation.surveyId(),
                status, participation.lengthInMinutes());
    }

    private URI createFileUri() throws URISyntaxException {
        return getClass().getClassLoader().getResource("Participation.csv").toURI();
    }
}
