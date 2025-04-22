package com.vitorhcoelho.enums;

public enum Category {
    BACKEND("Backend"), FRONTEND("Frontend");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

}
