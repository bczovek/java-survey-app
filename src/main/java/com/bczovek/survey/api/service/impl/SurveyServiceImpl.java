package com.bczovek.survey.api.service.impl;

import com.bczovek.survey.api.model.*;
import com.bczovek.survey.api.repository.MemberRepository;
import com.bczovek.survey.api.repository.ParticipationRepository;
import com.bczovek.survey.api.repository.SurveyRepository;
import com.bczovek.survey.api.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final MemberRepository memberRepository;
    private final SurveyRepository surveyRepository;
    private final ParticipationRepository participationRepository;

    @Override
    public List<Member> getAllRespondentsBySurvey(Integer surveyId) {
        List<Participation> participationList = participationRepository.getParticipationBySurvey(surveyId);
        List<Integer> memberIds = participationList.stream().map(Participation::memberId).toList();
        return memberRepository.getMembersByIds(memberIds);
    }

    @Override
    public List<Survey> getAllCompletedSurveysByMember(Integer memberId) {
        List<Participation> participationList = participationRepository.getParticipationByMember(memberId);
        List<Integer> completedSurveyIds = participationList.stream()
                .filter(participation -> participation.status().equals(ParticipationStatus.COMPLETED))
                .map(Participation::surveyId)
                .toList();
        return surveyRepository.getSurveysByIds(completedSurveyIds);
    }

    @Override
    public MemberPoints getPointsCollectedByMember(Integer memberId) {
        return null;
    }

    @Override
    public List<Member> getAvailableMembersForSurvey(Integer surveyId) {
        return List.of();
    }

    @Override
    public List<SurveyStatistics> getStatisticsForAllSurveys() {
        return List.of();
    }
}
