package com.bczovek.survey.csv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"Member Id", "Survey Id", "Status", "Length"})
public record Participation(@JsonProperty("Member Id") int memberId,
                            @JsonProperty("Survey Id") int surveyId,
                            @JsonProperty("Status") int status,
                            @JsonProperty("Length") int lengthInMinutes) {

}
