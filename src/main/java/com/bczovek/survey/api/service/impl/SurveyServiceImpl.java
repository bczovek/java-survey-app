package com.bczovek.survey.api.service.impl;

import com.bczovek.survey.api.model.*;
import com.bczovek.survey.api.repository.MemberRepository;
import com.bczovek.survey.api.repository.ParticipationRepository;
import com.bczovek.survey.api.repository.SurveyRepository;
import com.bczovek.survey.api.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final MemberRepository memberRepository;
    private final SurveyRepository surveyRepository;
    private final ParticipationRepository participationRepository;

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
        return surveys.stream()
                .map(this::createStatisticsForSurvey)
                .toList();
    }

    private SurveyStatistics createStatisticsForSurvey(Survey survey) {
        List<Participation> participations = participationRepository.getParticipationBySurvey(survey.id());
        Long completed = countByStatus(participations, ParticipationStatus.COMPLETED);
        Long filtered = countByStatus(participations, ParticipationStatus.FILTERED);
        Long rejected = countByStatus(participations, ParticipationStatus.REJECTED);
        Double averageLengthInMinutes = countLengthAverage(participations);
        return new SurveyStatistics(survey.id(), survey.name(), completed, filtered, rejected, averageLengthInMinutes);
    }

    private static Double countLengthAverage(List<Participation> participations) {
        OptionalDouble optionalAverage = participations.stream()
                .mapToDouble(Participation::lengthInMinutes)
                .average();
        return optionalAverage.isPresent() ? optionalAverage.getAsDouble() : 0;
    }

    private static long countByStatus(List<Participation> participations, ParticipationStatus status) {
        return participations.stream()
                .filter(participation -> participation.status().equals(status))
                .count();
    }
}
