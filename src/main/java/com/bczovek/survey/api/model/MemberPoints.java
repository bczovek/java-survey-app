package com.bczovek.survey.api.model;

import java.util.List;

public record MemberPoints(Integer memberId, List<SurveyPoints> surveyPoints) {
}
