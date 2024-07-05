package com.bczovek.survey.api.repository;

import com.bczovek.survey.api.model.Member;

import java.util.List;

public interface MemberRepository {

    List<Member> getAllActiveMembers();
}
