package com.bczovek.survey.api.repository.config;

import com.bczovek.survey.api.repository.impl.InMemoryMemberRepository;
import com.bczovek.survey.api.repository.impl.InMemoryParticipationRepository;
import com.bczovek.survey.api.repository.impl.InMemorySurveyRepository;
import com.bczovek.survey.csv.parser.MembersParser;
import com.bczovek.survey.csv.parser.ParticipationParser;
import com.bczovek.survey.csv.parser.SurveyParser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CsvToInMemoryRepositoryConfig {

    private final MembersParser membersParser;
    private final SurveyParser surveyParser;
    private final ParticipationParser participationParser;

    @Bean
    public InMemoryMemberRepository inMemoryMemberRepository() {
        return new InMemoryMemberRepository(membersParser.parse());
    }

    @Bean
    public InMemorySurveyRepository inMemorySurveyRepository() {
        return new InMemorySurveyRepository(surveyParser.parse());
    }

    @Bean
    public InMemoryParticipationRepository inMemoryParticipationRepository() {
        return new InMemoryParticipationRepository(participationParser.parse());
    }
}
