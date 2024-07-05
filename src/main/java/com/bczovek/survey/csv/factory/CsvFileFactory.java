package com.bczovek.survey.csv.factory;

import com.bczovek.survey.csv.exception.InvalidCsvFileReference;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

@Component
public class CsvFileFactory {

    public File createFileFromResource(String resourceName) {
        try {
            return new File(createResourceURL(resourceName).toURI());
        } catch (URISyntaxException e) {
            throw new InvalidCsvFileReference(STR."Could not find \{resourceName} CSV file in the resources", e);
        }
    }

    private URL createResourceURL(String resourceName) {
        URL resource = CsvFileFactory.class.getClassLoader().getResource(resourceName);
        if (resource == null) {
            throw new InvalidCsvFileReference(STR."Could not find \{resourceName} CSV file in the resources");
        } else {
            return resource;
        }
    }
}
