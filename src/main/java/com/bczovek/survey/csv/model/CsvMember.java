package com.bczovek.survey.csv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "Member id", "Full name", "E-mail address", "Is Active"})
public record CsvMember(@JsonProperty("Member id") int id,
                        @JsonProperty("Full name") String fullName,
                        @JsonProperty("E-mail address") String email,
                        @JsonProperty("Is Active") int isActive) {

}


