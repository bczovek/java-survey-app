package com.bczovek.survey.csv.parser;

import com.bczovek.survey.api.model.Member;
import com.bczovek.survey.csv.factory.CsvFileIteratorFactory;
import com.bczovek.survey.csv.model.CsvMember;
import com.fasterxml.jackson.databind.MappingIterator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MembersParser {

    private final CsvFileIteratorFactory iteratorFactory;

    public Map<Integer, Member> parse() {
        try(MappingIterator<CsvMember> membersIterator =
                    iteratorFactory.createFromFile(createFileUri(), CsvMember.class)) {
            Map<Integer, Member> memberMap = new HashMap<>();
            while (membersIterator.hasNext()) {
                Member member = convertToDTO(membersIterator.next());
                memberMap.put(member.id(), member);
            }
            return memberMap;
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private URI createFileUri() throws URISyntaxException {
        return getClass().getClassLoader().getResource("Members.csv").toURI();
    }

    private Member convertToDTO(CsvMember csvMember) {
        return new Member(csvMember.id(), csvMember.fullName(), csvMember.email(), csvMember.isActive() == 1);
    }
}
