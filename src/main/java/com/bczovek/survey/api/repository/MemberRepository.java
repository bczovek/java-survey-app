package com.bczovek.survey.api.repository;

import com.bczovek.survey.api.model.MemberDTO;

import java.util.List;

public interface MemberRepository {

    MemberDTO getMemberById(Integer id);
    List<MemberDTO> getMembersById(List<Integer> ids);
}
