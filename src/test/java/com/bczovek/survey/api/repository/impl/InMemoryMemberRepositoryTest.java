package com.bczovek.survey.api.repository.impl;

import com.bczovek.survey.api.model.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryMemberRepositoryTest {

    private static final Map<Integer, Member> testMembers = new HashMap<>();

    private final InMemoryMemberRepository underTest = new InMemoryMemberRepository(testMembers);

    @BeforeAll
    static void beforeAll() {
        for(int i = 0; i < 5; i++) {
            testMembers.put(i, new Member(i, "", "", i % 2 == 0));
        }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllActiveMembersShouldReturnOnlyActiveMembers() {
        List<Member> expected = new ArrayList<>();
        expected.add(new Member(0, "", "", true));
        expected.add(new Member(2, "", "", true));
        expected.add(new Member(4, "", "", true));

        List<Member> actual = underTest.getAllActiveMembers();

        assertEquals(expected, actual);
    }
}