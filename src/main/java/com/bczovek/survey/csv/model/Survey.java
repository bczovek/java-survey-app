package com.bczovek.survey.csv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"Survey Id", "Name", "Expected completes", "Completion points", "Filtered points"})
public record Survey(@JsonProperty("Survey Id") int id,
                     @JsonProperty("Name") String name,
                     @JsonProperty("Expected completes") int expectedCompletes,
                     @JsonProperty("Completion points") int completionPoints,
                     @JsonProperty("Filtered points") int filteredPoints) {
}
