package com.bczovek.survey.api.repository.impl;

import com.bczovek.survey.api.model.MemberDTO;
import com.bczovek.survey.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class InMemoryMemberRepository implements MemberRepository {

    private final Map<Integer, MemberDTO> members;

    @Override
    public MemberDTO getMemberById(Integer id) {
        return members.get(id);
    }

    @Override
    public List<MemberDTO> getMembersById(List<Integer> ids) {
        return List.of();
    }
}
