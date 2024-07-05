package com.bczovek.survey.csv.parser;

import com.bczovek.survey.api.model.Member;
import com.bczovek.survey.csv.util.URIUtil;
import com.bczovek.survey.csv.exception.InvalidCsvFileContent;
import com.bczovek.survey.csv.factory.CsvFileIteratorFactory;
import com.bczovek.survey.csv.model.CsvMember;
import com.fasterxml.jackson.databind.MappingIterator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.StringTemplate.STR;

@Component
@RequiredArgsConstructor
public class MembersParser {

    @Value("${members.file}")
    private String memberCsvFile;
    private final CsvFileIteratorFactory iteratorFactory;

    public Map<Integer, Member> parse() {
        try(MappingIterator<CsvMember> membersIterator =
                    iteratorFactory.createFromFile(URIUtil.createFileUriFromResource(memberCsvFile), CsvMember.class)) {
            Map<Integer, Member> memberMap = new HashMap<>();
            while (membersIterator.hasNext()) {
                Member member = convertToDTO(membersIterator.next());
                memberMap.put(member.id(), member);
            }
            return memberMap;
        } catch (IOException e) {
            throw new InvalidCsvFileContent(STR."Error occured while processing \{memberCsvFile}", e);
        }
    }

    private Member convertToDTO(CsvMember csvMember) {
        return new Member(csvMember.id(), csvMember.fullName(), csvMember.email(), csvMember.isActive() == 1);
    }
}
