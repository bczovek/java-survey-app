package com.bczovek.survey.api.repository.config;

import com.bczovek.survey.api.repository.impl.InMemoryMemberRepository;
import com.bczovek.survey.api.repository.impl.InMemoryParticipationRepository;
import com.bczovek.survey.api.repository.impl.InMemorySurveyRepository;
import com.bczovek.survey.csv.CsvLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CsvToInMemoryRepositoryConfig {

    private final CsvLoader csvLoader;

    @Bean
    public InMemoryMemberRepository inMemoryMemberRepository() {
        return new InMemoryMemberRepository(csvLoader.getMembers());
    }

    @Bean
    public InMemorySurveyRepository inMemorySurveyRepository() {
        return new InMemorySurveyRepository(csvLoader.getSurveys());
    }

    @Bean
    public InMemoryParticipationRepository inMemoryParticipationRepository() {
        return new InMemoryParticipationRepository(csvLoader.getParticipations());
    }
}
