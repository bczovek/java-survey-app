package com.bczovek.survey.api.service;

import com.bczovek.survey.api.model.Member;
import com.bczovek.survey.api.model.MemberPoints;
import com.bczovek.survey.api.model.Survey;
import com.bczovek.survey.api.model.SurveyStatistics;

import java.util.List;

public interface SurveyService {

    List<Member> getAllRespondentsBySurvey(Integer surveyId);
    List<Survey> getAllCompletedSurveysByMember(Integer memberId);
    MemberPoints getPointsCollectedByMember(Integer memberId);
    List<Member> getAvailableMembersForSurvey(Integer surveyId);
    List<SurveyStatistics> getStatisticsForAllSurveys();
}
