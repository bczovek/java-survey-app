package com.bczovek.survey.csv.factory;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class CsvFileIteratorFactory {

    private final CsvMapper csvMapper = new CsvMapper();

    public <T> MappingIterator<T> createFromFile(URI fileUri, Class<T> targetObject)
            throws URISyntaxException, IOException {
        return csvMapper.readerFor(targetObject)
                .with(createSchema(targetObject))
                .readValues(getFile(fileUri));
    }

    private <T> CsvSchema createSchema(Class<T> targetObject) {
        return csvMapper.typedSchemaFor(targetObject)
                .withHeader()
                .withColumnSeparator(',');
    }

    private File getFile(URI fileUri) {
        return new File(fileUri);
    }
}
