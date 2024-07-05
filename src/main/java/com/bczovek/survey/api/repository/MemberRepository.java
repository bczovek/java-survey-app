package com.bczovek.survey.api.repository;

import com.bczovek.survey.api.model.Member;

import java.util.List;

public interface MemberRepository {

    Member getMemberById(Integer id);
    List<Member> getMembersById(List<Integer> ids);
}
