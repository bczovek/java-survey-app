package com.bczovek.survey.api.model;

public record Member(Integer id,
                     String fullName,
                     String email,
                     Boolean isActive) {
}
