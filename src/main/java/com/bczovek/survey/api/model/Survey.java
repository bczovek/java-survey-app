package com.bczovek.survey.api.model;

public record Survey(Integer id, String name,
                     Integer expectedCompletes,
                     Integer completionPoints,
                     Integer filteredPoints) {
}
