package com.bczovek.survey.api.repository.impl;

import com.bczovek.survey.api.model.Member;
import com.bczovek.survey.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class InMemoryMemberRepository implements MemberRepository {

    private final Map<Integer, Member> members;

    @Override
    public Member getMemberById(Integer id) {
        return members.get(id);
    }

    @Override
    public List<Member> getMembersById(List<Integer> ids) {
        return List.of();
    }
}
