package com.bczovek.survey.csv.factory;

import com.bczovek.survey.csv.exception.InvalidCsvFileContent;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;

@Component
public class CsvFileIteratorFactory {

    private final CsvMapper csvMapper = new CsvMapper();

    public <T> MappingIterator<T> createFromFile(File file, Class<T> targetObject) {
        try {
            return csvMapper.readerFor(targetObject)
                    .with(createSchema(targetObject))
                    .readValues(file);
        } catch (IOException e) {
            throw new InvalidCsvFileContent(STR."Error occured while processing \{file}", e);
        }
    }

    private <T> CsvSchema createSchema(Class<T> targetObject) {
        return csvMapper.typedSchemaFor(targetObject)
                .withHeader()
                .withColumnSeparator(',');
    }
}
