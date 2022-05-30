package com.yologger.heart_to_heart_api.repository.member;

public enum AuthorityType {
    ADMIN("ADMIN"),
    USER("USER");

    private String description;

    AuthorityType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
