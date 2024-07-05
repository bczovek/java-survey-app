package com.bczovek.survey.csv;

import com.bczovek.survey.api.model.Member;
import com.bczovek.survey.api.model.Participation;
import com.bczovek.survey.api.model.Survey;
import com.bczovek.survey.csv.parser.MembersParser;
import com.bczovek.survey.csv.parser.ParticipationParser;
import com.bczovek.survey.csv.parser.SurveyParser;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CsvLoader {

    private final MembersParser membersParser;
    private final SurveyParser surveyParser;
    private final ParticipationParser participationParser;

    @Getter
    private Map<Integer, Member> members;
    @Getter
    private Map<Integer, Survey> surveys;
    @Getter
    private List<Participation> participations;

    @PostConstruct
    public void init() {
        members = membersParser.parse();
        surveys = surveyParser.parse();
        participations = participationParser.parse(members, surveys);
    }
}
