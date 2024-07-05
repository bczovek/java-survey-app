package com.bczovek.survey.csv.parser;

import com.bczovek.survey.api.model.Member;
import com.bczovek.survey.csv.exception.InvalidCsvFileContent;
import com.bczovek.survey.csv.factory.CsvFileFactory;
import com.bczovek.survey.csv.factory.CsvFileIteratorFactory;
import com.bczovek.survey.csv.model.CsvMember;
import com.fasterxml.jackson.databind.MappingIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MembersParserTest {

    @Mock
    private CsvFileIteratorFactory mockIteratorFactory;
    @Mock
    private CsvFileFactory mockCsvFileFactory;
    @InjectMocks
    private MembersParser underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testParseShouldReturnConvertedMemberObjects() {
        File mockFile = mock(File.class);
        MappingIterator<CsvMember> mockIterator = mock(MappingIterator.class);
        when(mockCsvFileFactory.createFileFromResource(any())).thenReturn(mockFile);
        when(mockIteratorFactory.createFromFile(mockFile, CsvMember.class))
                .thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(mockIterator.next()).thenReturn(new CsvMember(1, "Test", "test", 1))
                .thenReturn(new CsvMember(2, "Test", "test", 0));
        Member expected1 = new Member(1, "Test", "test", true);
        Member expected2 = new Member(2, "Test", "test", false);

        Map<Integer, Member> actual = underTest.parse();

        assertEquals(expected1, actual.get(1));
        assertEquals(expected2, actual.get(2));
    }

    @Test
    void testParseWhenIOExceptionOccursShouldThrowInvalidCsvFileContentException() throws IOException {
        File mockFile = mock(File.class);
        MappingIterator<CsvMember> mockIterator = mock(MappingIterator.class);
        when(mockCsvFileFactory.createFileFromResource(any())).thenReturn(mockFile);
        when(mockIteratorFactory.createFromFile(mockFile, CsvMember.class))
                .thenReturn(mockIterator);
        doThrow(IOException.class).when(mockIterator).close();

        assertThrows(InvalidCsvFileContent.class, () -> underTest.parse());
    }
}