package com.bczovek.survey.api.repository;

import com.bczovek.survey.api.model.Member;

import java.util.List;

public interface MemberRepository {

    List<Member> getAllActiveMembers();
    Member getMemberById(Integer id);
    List<Member> getMembersByIds(List<Integer> ids);
}
