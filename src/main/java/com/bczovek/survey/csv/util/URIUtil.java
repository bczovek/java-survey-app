package com.bczovek.survey.csv.util;

import com.bczovek.survey.csv.exception.InvalidCsvFileReference;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URIUtil {

    private URIUtil() {
    }

    public static URI createFileUriFromResource(String resourceName) {
        try {
            return createResourceURL(resourceName).toURI();
        } catch (URISyntaxException e) {
            throw new InvalidCsvFileReference(STR."Could not find \{resourceName} CSV file in the resources", e);
        }
    }

    private static URL createResourceURL(String resourceName) {
        URL resource = URIUtil.class.getClassLoader().getResource(resourceName);
        if (resource == null) {
            throw new InvalidCsvFileReference(STR."Could not find \{resourceName} CSV file in the resources");
        } else {
            return resource;
        }
    }
}
