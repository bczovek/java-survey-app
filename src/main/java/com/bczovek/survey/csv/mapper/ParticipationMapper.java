package com.bczovek.survey.csv.mapper;

import com.bczovek.survey.api.model.Member;
import com.bczovek.survey.api.model.Participation;
import com.bczovek.survey.api.model.ParticipationStatus;
import com.bczovek.survey.api.model.Survey;
import com.bczovek.survey.csv.model.RawParticipation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ParticipationMapper {

    public List<Participation> mapMembersAndSurveys(List<RawParticipation> rawParticipations,
                                                      Map<Integer, Member> members,
                                                      Map<Integer, Survey> surveys) {
        return rawParticipations.stream().map(rawParticipation ->
            mapMemberAndSurvey(rawParticipation, members.get(rawParticipation.memberId()),
                    surveys.get(rawParticipation.surveyId())))
                .toList();
    }

    private Participation mapMemberAndSurvey(RawParticipation rawParticipation, Member member, Survey survey) {
        ParticipationStatus status = ParticipationStatus.values()[rawParticipation.status() - 1];
        return new Participation(member, survey, status, rawParticipation.lengthInMinutes());
    }

}
