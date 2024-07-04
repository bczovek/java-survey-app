package com.bczovek.survey.csv.parser;

import com.bczovek.survey.api.model.MemberDTO;
import com.bczovek.survey.csv.model.Member;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class MembersParser {

    private final CsvMapper csvMapper = new CsvMapper();

    public Map<Integer, MemberDTO> parse() {
        try(MappingIterator<Member> membersIterator = createIteratorFromCsvFile()) {
            Map<Integer, MemberDTO> memberMap = new HashMap<>();
            while(membersIterator.hasNext()) {
                MemberDTO member = convertToDTO(membersIterator.next());
                memberMap.put(member.id(), member);
            }
            return memberMap;
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private MappingIterator<Member> createIteratorFromCsvFile() throws URISyntaxException, IOException {
        return csvMapper.readerFor(Member.class)
                    .with(getSchema())
                    .readValues(getFile());
    }

    private CsvSchema getSchema() {
        return csvMapper.typedSchemaFor(Member.class)
                .withHeader()
                .withColumnSeparator(',');
    }

    private File getFile() throws URISyntaxException {
        return new File(getClass().getClassLoader().getResource("Members.csv").toURI());
    }

    private MemberDTO convertToDTO(Member member) {
        return new MemberDTO(member.id(), member.fullName(), member.email(), member.isActive() == 1);
    }
}
