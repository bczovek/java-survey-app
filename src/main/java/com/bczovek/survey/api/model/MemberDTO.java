package com.bczovek.survey.api.model;

public record MemberDTO(Integer id,
                        String fullName,
                        String email,
                        Boolean isActive) {
}
