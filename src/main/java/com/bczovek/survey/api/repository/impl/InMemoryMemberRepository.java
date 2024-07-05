package com.bczovek.survey.api.repository.impl;

import com.bczovek.survey.api.model.Member;
import com.bczovek.survey.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class InMemoryMemberRepository implements MemberRepository {

    private final Map<Integer, Member> members;

    @Override
    public Member getMemberById(Integer id) {
        return members.get(id);
    }

    @Override
    public List<Member> getMembersByIds(List<Integer> ids) {
        return members.entrySet()
                .stream()
                .filter(memberEntry -> ids.contains(memberEntry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
