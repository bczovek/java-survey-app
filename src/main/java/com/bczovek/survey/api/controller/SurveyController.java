package com.bczovek.survey.api.controller;

import com.bczovek.survey.api.model.Member;
import com.bczovek.survey.api.model.MemberPoints;
import com.bczovek.survey.api.model.Survey;
import com.bczovek.survey.api.model.SurveyStatistics;
import com.bczovek.survey.api.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SurveyController {

    private final SurveyService surveyService;

    @GetMapping("/respondents")
    public List<Member> getRespondentsForSurvey(@RequestParam Integer surveyId) {
        return surveyService.getAllRespondentsBySurvey(surveyId);
    }

    @GetMapping("/completions")
    public List<Survey> getAllCompletedSurveysByMember(@RequestParam Integer memberId) {
        return surveyService.getAllCompletedSurveysByMember(memberId);
    }

    @GetMapping("/points")
    public MemberPoints getPointsCollectedByMember(@RequestParam Integer memberId) {
        return surveyService.getPointsCollectedByMember(memberId);
    }

    @GetMapping("/available-members")
    public List<Member> getAvailableMembersForSurvey(@RequestParam Integer surveyId) {
        return surveyService.getAvailableMembersForSurvey(surveyId);
    }

    @GetMapping("/survey-statistics")
    public List<SurveyStatistics> getStatisticsForAllSurveys() {
        return surveyService.getStatisticsForAllSurveys();
    }
}
