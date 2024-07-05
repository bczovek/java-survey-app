package com.bczovek.survey.api.service.impl;

import com.bczovek.survey.api.cache.SurveyStatisticsCache;
import com.bczovek.survey.api.model.*;
import com.bczovek.survey.api.repository.MemberRepository;
import com.bczovek.survey.api.repository.ParticipationRepository;
import com.bczovek.survey.api.repository.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SurveyServiceImplTest {

    @Mock
    private SurveyRepository surveyRepository;
    @Mock
    private ParticipationRepository participationRepository;
    @Mock
    private SurveyStatisticsCache surveyStatisticsCache;
    @InjectMocks
    private SurveyServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStatisticsForAllSurveysShouldReturnNewSurveyStatisticsAndCache() {
        Member member = new Member(1, "", "", true);
        Survey survey = new Survey(1, "Name", 10, 2, 1);
        when(surveyRepository.getAllSurveys())
                .thenReturn(List.of(survey));
        when(participationRepository.getParticipationBySurvey(1))
                .thenReturn(List.of(new Participation(member, survey, ParticipationStatus.COMPLETED, 10)));

        SurveyStatistics expectedSurveyStatistics = new SurveyStatistics(1, "Name",
                1L, 0L, 0L, 10.0);
        List<SurveyStatistics> expected = List.of(expectedSurveyStatistics);

        List<SurveyStatistics> actual = underTest.getStatisticsForAllSurveys();

        assertEquals(expected, actual);
        verify(surveyStatisticsCache).retrieve(1);
        verify(surveyStatisticsCache).cache(1, expectedSurveyStatistics);
    }

    @Test
    void testGetStatisticsForAllSurveysShouldReturnCachedStatistics() {
        when(surveyRepository.getAllSurveys())
                .thenReturn(List.of(new Survey(1, "Name", 10, 2, 1)));
        SurveyStatistics expectedSurveyStatistics = new SurveyStatistics(1, "Name",
                1L, 0L, 0L, 10.0);
        when(surveyStatisticsCache.retrieve(1)).thenReturn(Optional.of(expectedSurveyStatistics));
        List<SurveyStatistics> expected = List.of(expectedSurveyStatistics);

        List<SurveyStatistics> actual = underTest.getStatisticsForAllSurveys();

        assertEquals(expected, actual);
        verify(surveyStatisticsCache).retrieve(1);
        verifyNoMoreInteractions(surveyStatisticsCache);
    }
}