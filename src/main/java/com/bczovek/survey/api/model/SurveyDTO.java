package com.bczovek.survey.api.model;

public record SurveyDTO(Integer id, String name,
                        Integer expectedCompletes,
                        Integer completionPoints,
                        Integer filteredPoints) {
}
