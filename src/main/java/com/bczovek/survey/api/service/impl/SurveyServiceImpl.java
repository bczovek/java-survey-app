package com.bczovek.survey.api.service.impl;

import com.bczovek.survey.api.cache.SurveyStatisticsCache;
import com.bczovek.survey.api.model.*;
import com.bczovek.survey.api.repository.MemberRepository;
import com.bczovek.survey.api.repository.ParticipationRepository;
import com.bczovek.survey.api.repository.SurveyRepository;
import com.bczovek.survey.api.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final MemberRepository memberRepository;
    private final SurveyRepository surveyRepository;
    private final ParticipationRepository participationRepository;
    private final SurveyStatisticsCache surveyStatisticsCache;

    @Override
    public List<Member> getAllRespondentsBySurvey(Integer surveyId) {
        List<Participation> participationList = participationRepository.getParticipationBySurvey(surveyId);
        return participationList.stream().map(Participation::member).toList();
    }

    @Override
    public List<Survey> getAllCompletedSurveysByMember(Integer memberId) {
        List<Participation> participationList = participationRepository.getParticipationByMember(memberId);
        return participationList.stream()
                .filter(participation -> participation.status().equals(ParticipationStatus.COMPLETED))
                .map(Participation::survey)
                .toList();
    }

    @Override
    public MemberPoints getPointsCollectedByMember(Integer memberId) {
        List<Participation> participationList = participationRepository.getParticipationByMember(memberId);
        List<SurveyPoints> surveyPointsList = createSurveyPointsList(participationList);
        return new MemberPoints(memberId, surveyPointsList);
    }

    private List<SurveyPoints> createSurveyPointsList(List<Participation> participationList) {
        return participationList.stream()
                .filter(this::isCompletedOrFiltered)
                .map(this::convertParticipationToSurveyPoints)
                .toList();
    }

    private boolean isCompletedOrFiltered(Participation participation) {
        return participation.status().equals(ParticipationStatus.COMPLETED) ||
                participation.status().equals(ParticipationStatus.FILTERED);
    }

    private SurveyPoints convertParticipationToSurveyPoints(Participation participation) {
        Integer pointCollected = 0;
        if (participation.status().equals(ParticipationStatus.COMPLETED))
            pointCollected = participation.survey().completionPoints();
        else if (participation.status().equals(ParticipationStatus.FILTERED))
            pointCollected = participation.survey().filteredPoints();

        return new SurveyPoints(participation.survey().id(), pointCollected);
    }

    @Override
    public List<Member> getAvailableMembersForSurvey(Integer surveyId) {
        List<Member> participatedMembers = participationRepository.getParticipationBySurvey(surveyId)
                .stream()
                .map(Participation::member)
                .toList();
        List<Member> activeMembers = memberRepository.getAllActiveMembers();
        return activeMembers.stream()
                .filter(activeMember -> !participatedMembers.contains(activeMember))
                .toList();
    }

    @Override
    public List<SurveyStatistics> getStatisticsForAllSurveys() {
        List<Survey> surveys = surveyRepository.getAllSurveys();
        List<SurveyStatistics> surveyStatistics = new ArrayList<>();
        for (Survey survey : surveys) {
            Optional<SurveyStatistics> optionalSurveyStatistics = surveyStatisticsCache.retrieve(survey.id());
            if(optionalSurveyStatistics.isPresent()) {
                surveyStatistics.add(optionalSurveyStatistics.get());
            } else {
                SurveyStatistics newSurveyStatistics = createStatisticsForSurvey(survey);
                surveyStatistics.add(newSurveyStatistics);
                surveyStatisticsCache.cache(survey.id(), newSurveyStatistics);
            }
        }
        return surveyStatistics;
    }

    private SurveyStatistics createStatisticsForSurvey(Survey survey) {
        List<Participation> participations = participationRepository.getParticipationBySurvey(survey.id());
        Long completed = countParticipantsByStatus(participations, ParticipationStatus.COMPLETED);
        Long filtered = countParticipantsByStatus(participations, ParticipationStatus.FILTERED);
        Long rejected = countParticipantsByStatus(participations, ParticipationStatus.REJECTED);
        Double averageLengthInMinutes = countLengthAverage(participations);
        return new SurveyStatistics(survey.id(), survey.name(), completed, filtered, rejected, averageLengthInMinutes);
    }

    private Double countLengthAverage(List<Participation> participations) {
        OptionalDouble optionalAverage = participations.stream()
                .mapToDouble(Participation::lengthInMinutes)
                .average();
        return optionalAverage.isPresent() ? optionalAverage.getAsDouble() : 0;
    }

    private long countParticipantsByStatus(List<Participation> participations, ParticipationStatus status) {
        return participations.stream()
                .filter(participation -> participation.status().equals(status))
                .count();
    }
}
